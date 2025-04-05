package com.keygame.service.impl;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.keygame.common.OrderStatusEnum;
import com.keygame.common.PaymentMethodEnum;
import com.keygame.common.PaypalPaymentIntentEnum;
import com.keygame.common.PaypalPaymentMethodEnum;
import com.keygame.config.URLUtils;
import com.keygame.config.exceptionhandler.BusinessException;
import com.keygame.config.security.JwtUtils;
import com.keygame.dto.Mail;
import com.keygame.dto.request.CreateOrderDto;
import com.keygame.dto.request.ReportOrderDto;
import com.keygame.dto.response.*;
import com.keygame.entity.*;
import com.keygame.repository.*;
import com.keygame.service.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.keygame.common.ConfigConstant.PREPAID_FORGE;
import static com.keygame.common.OrderStatusEnum.*;
import static com.keygame.common.PaymentConstant.PAYPAL_CANCEL_URL;
import static com.keygame.common.PaymentConstant.PAYPAL_SUCCESS_URL;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    public static final String CREATE_ORDER_NO_PRODUCT = "There are no products in the order, please select the product you want to buy.";
    public static final String ABORT_PAYMENT = "We have received your order, but the payment was aborted. Please change your payment method or try again.";
    public static final String ERROR_PAYMENT = "We have received your order, but the payment was aborted. Do not worry. We are processing this order for you.";
    public static final String KEY_GAME = "keygame-";
    public static final String CODE_TYPE_TEXT = "TEXT";
    public static final String CODE_TYPE_SCAN = "SCAN";
    public static final String ORDER_PRICE_MUST_GREATER_ZERO = "Total order amount must be greater than 0";

    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    MailService emailService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PaypalService paypalService;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    CartService cartService;

    @Override
    public String save(CreateOrderDto orderDto, HttpServletRequest req) {
        String email = jwtUtils.getEmailFromJwtToken(jwtUtils.parseJwt(req));
        User user = userRepository.findByEmail(email);
        if (CollectionUtils.isEmpty(orderDto.getCartDto().getCartItems())) {
            throw BusinessException.builder().message(CREATE_ORDER_NO_PRODUCT).build();
        }
        CartInfoDto cartInfoDto = cartService.getCartInfo(orderDto.getCartDto());

        if (cartInfoDto.getTotalPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ORDER_PRICE_MUST_GREATER_ZERO);
        }

        Order order = new Order();
        order.setUserEmail(email);
        order.setUserId(user.getId());
        order.setSendEmail(email);
        order.setOrderStatus(OrderStatusEnum.SUBMITTED.toString());
        order.setSubPrice(cartInfoDto.getSubPrice());
        order.setTotalPrice(cartInfoDto.getTotalPrice());
        order.setPaymentFee(cartInfoDto.getPaymentFee());
        order.setCreatedAt(new Date());
        Order orderSubmitted = orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Product product : cartInfoDto.getProducts()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setBasePrice(product.getBasePrice());
            orderDetail.setIsMyStock(product.getSupplierPrice() == null
                    || product.getBasePrice().compareTo(product.getSupplierPrice()) < 0);
            orderDetail.setPrice(product.getPrice());
            orderDetail.setProductId(product.getId());
            orderDetail.setUserId(user.getId());
            orderDetail.setOrderId(orderSubmitted.getId());
            orderDetails.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetails);

        String cancelUrl = URLUtils.getBaseURl(req) + "/" + PAYPAL_CANCEL_URL + "?orderId=" + order.getId();
        String successUrl = URLUtils.getBaseURl(req) + "/" + PAYPAL_SUCCESS_URL + "?orderId=" + order.getId();
        try {
            Payment payment = paypalService.createPayment(
                    cartInfoDto.getTotalPrice(),
                    "EUR",
                    PaypalPaymentMethodEnum.paypal,
                    PaypalPaymentIntentEnum.sale,
                    "keygameStore.com - payment order " + order.getId(),
                    cancelUrl,
                    successUrl);
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    orderSubmitted.setOrderStatus(PAYMENT_PROCESS.toString());
                    orderSubmitted.setPaymentId(payment.getId());
//                    orderSubmitted.setPayerId(payment.getPayer().getPayerInfo().getPayerId());
                    orderSubmitted.setPaymentMethod(PaymentMethodEnum.paypal.toString());
                    orderRepository.save(orderSubmitted);
                    return links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error("CreatePayment paypal exception: ", e);
            throw new BusinessException(ERROR_PAYMENT);
        }
        return "false";
    }

    @Override
    public boolean executePayment(String paymentId, String payerId) {
        Order order = orderRepository.findByPaymentId(paymentId);
        if (order == null || !PAYMENT_PROCESS.toString().equalsIgnoreCase(order.getOrderStatus())) {
            return false;
        }
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return completeOrder(order);
            }
        } catch (PayPalRESTException e) {
            log.error("ExecutePayment Paypal error order: " + order.getId(), e);
            throw new BusinessException(ABORT_PAYMENT);
        }
        return false;
    }

    @Autowired
    KeyRepository keyRepository;
    public boolean completeOrder(Order order) {
        order.setPaymentDate(new Date());
        order.setOrderStatus(OrderStatusEnum.PAYMENT_SUCCESS.name());
        order.setUpdatedAt(new Date());
        orderRepository.save(order);
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
        boolean isGetKey = true;
        for (OrderDetail orderDetail : orderDetails) {
            isGetKey = getKey(orderDetail);
        }
        order.setUpdatedAt(new Date());
        order.setOrderStatus(isGetKey ? GET_KEY_SUCCESS.name() : GET_KEY_FAIL.name());
        orderRepository.save(order);
        return true;
    }

    private boolean getKey(OrderDetail orderDetail) {
        if (orderDetail.getIsMyStock()) {
            return getKeyFromStock(orderDetail);
        } else {

            Key key = new Key();
            keyRepository.save(key);

            orderDetail.setKeyId(key.getId());
            orderDetail.setKey(key.getCode());
            orderDetail.setBasePrice(orderDetail.getBasePrice());
            orderDetailRepository.save(orderDetail);

            return true;
        }
    }

    @Synchronized
    private boolean getKeyFromStock(OrderDetail orderDetail) {
        Key key = keyRepository.getKey(orderDetail.getProductId());
        orderDetail.setUpdatedAt(new Date());
        if (key == null) {
            orderDetail.setOutOfKeys(true);
            orderDetailRepository.save(orderDetail);
            return false;
        }
        key.setIsOrdered(true);
        key.setOrderId(orderDetail.getOrderId());
        key.setOrderDetailId(orderDetail.getId());
        key.setUpdatedAt(new Date());
        orderDetail.setKeyId(key.getId());
        orderDetail.setKey(key.getCode());
        orderDetail.setBasePrice(key.getBasePrice());
        keyRepository.save(key);
        orderDetailRepository.save(orderDetail);
        Optional<Inventory> optionalInventory = inventoryRepository.findById(key.getProductId());
        if (optionalInventory.isPresent()) {
            Inventory inventory = optionalInventory.get();
            inventory.setInStockQuantity(inventory.getInStockQuantity() - 1);
            inventory.setSaleQuantity(inventory.getSaleQuantity() + 1);
            inventoryRepository.save(inventory);
        }
        return true;
    }

    @Override
    public List<OrderDto> findAll(HttpServletRequest request) {
        User user = userRepository.findByEmail(jwtUtils.getEmailFromJwtToken(jwtUtils.parseJwt(request)));
        List<OrderDto> orderDtos = orderRepository.findAllByUserId(user.getId()).stream().map(entity -> modelMapper.map(entity, OrderDto.class)).collect(Collectors.toList());
        for (OrderDto orderDto : orderDtos) {
            List<OrderDetailDto> orderDetailDtos = orderDetailRepository.findByOrderId(orderDto.getId()).stream()
                    .map(entity -> {
                        OrderDetailDto orderDetailDto = modelMapper.map(entity, OrderDetailDto.class);
                        Optional<Product> product = productRepository.findById(orderDetailDto.getProductId());
                        if (product.isPresent()) {
                            orderDetailDto.setProductName(product.get().getName());
                            orderDetailDto.setHandle(product.get().getHandle());
                            orderDetailDto.setImage(product.get().getImage());
                        }
                        return orderDetailDto;
                    })
                    .toList();
            orderDto.setOrderDetailDtos(orderDetailDtos);
        }
        return orderDtos;
    }

    @Value("${spring.mail.from}")
    private String mailFrom;

    @Override
    public boolean cancelOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (!optionalOrder.isPresent() || !PAYMENT_PROCESS.toString().equalsIgnoreCase(optionalOrder.get().getOrderStatus())) {
            return false;
        }
        Order order = optionalOrder.get();
        order.setOrderStatus(OrderStatusEnum.PAYMENT_FAIL.toString());
        order.setUpdatedAt(new Date());

        log.info("START... Sending email cancel order");
        Mail mail = new Mail();
        mail.setFrom(mailFrom);//replace with your desired email
        mail.setMailTo(order.getSendEmail());//replace with your desired email
        mail.setSubject("Order processed " + order.getId());
        mail.setTemplate("cancel-order");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("orderId", order.getId());
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm");
        model.put("createdAt", format.format(order.getCreatedAt()));
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
        model.put("orderDetails", orderDetails);
        mail.setProps(model);
        try {
            emailService.sendEmail(mail);
        } catch (MessagingException e) {
            log.error("Send email cancel order fail ", e);
        }
        order.setIsSendEmail(true);
        orderRepository.save(order);

        log.info("END... Email sent success cancel order");
        return true;
    }

    @Override
    public ResponseReportOrderDto searchOrder(ReportOrderDto reportOrderDto) {
        List<Order> orders = orderRepository.searchOrder(reportOrderDto);
        ResponseReportOrderDto response = new ResponseReportOrderDto();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Order order : orders) {
            totalPrice = totalPrice.add(order.getTotalPrice());
        }
        response.setOrders(orders.stream().map(entity -> modelMapper.map(entity, OrderFullDto.class)).toList());
        response.setTotalPrice(totalPrice);
        return response;
    }
}
