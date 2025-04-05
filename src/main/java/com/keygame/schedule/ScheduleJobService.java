package com.keygame.schedule;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.keygame.common.OrderStatusEnum;
import com.keygame.dto.Mail;
import com.keygame.entity.Order;
import com.keygame.entity.OrderDetail;
import com.keygame.entity.PaymentInfo;
import com.keygame.entity.ScheduleJob;
import com.keygame.repository.OrderDetailRepository;
import com.keygame.repository.OrderRepository;
import com.keygame.repository.PaymentInfoRepository;
import com.keygame.repository.ScheduleJobRepository;
import com.keygame.service.MailService;
import com.keygame.service.OrderService;
import com.keygame.service.PaypalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class ScheduleJobService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    PaypalService paypalService;

    @Autowired
    ScheduleJobRepository scheduleJobRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    MailService mailService;

    @Value("${spring.mail.from}")
    private String mailFrom;

    @Autowired
    PaymentInfoRepository paymentInfoRepository;

//    @Scheduled(cron = "0 0/2 * * * ?")
    public void schedulePaymentStatus() {
        List<Order> orders = orderRepository.findOrderPaymentProcessing();
        for (Order order: orders) {
            log.info("START... schedule orderId: " + order.getId());
            try {
                Payment payment = paypalService.executePayment(order.getPaymentId(), order.getPayerId());
                if (payment.getState().equals("approved")) {
                    orderService.completeOrder(order);
                }
            } catch (PayPalRESTException e) {
                order.setOrderStatus(OrderStatusEnum.PAYMENT_FAIL.toString());
                orderRepository.save(order);

                PaymentInfo paymentInfo = new PaymentInfo();
                paymentInfo.setPaymentMethod(order.getPaymentMethod());
                paymentInfo.setOrderId(order.getId());
                paymentInfo.setCustomerId(order.getUserId());
                paymentInfo.setPayerId(order.getPayerId());
                paymentInfo.setOrderStatus(order.getOrderStatus());
                paymentInfo.setPaymentErrorName(e.getDetails().getName());
                paymentInfo.setPaymentErrorMessage(e.getDetails().getMessage());
                paymentInfoRepository.save(paymentInfo);
                log.error("executePayment Paypal error order: " + order.getId(), e);
            }
            log.info("End... schedule payment orderId: " + order.getId());
        }
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void scheduleSendMail() {
        List<Order> orders = orderRepository.findOrderSuccess();
        for (Order order: orders) {
            log.info("START... Sending email order: " + order);
            Mail mail = new Mail();
            mail.setFrom(mailFrom);
            mail.setMailTo(order.getSendEmail());
            mail.setSubject("Thank You for Your Purchase! Order Confirmation " + order.getId());
            mail.setTemplate("success-order");
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("orderId", order.getId());
            SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm");
            model.put("createdAt", format.format(order.getCreatedAt()));
            model.put("totalAmount", order.getTotalPrice());
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
            model.put("orderDetails", orderDetails);
            mail.setProps(model);
            try {
                mailService.sendEmail(mail);
                order.setOrderStatus(OrderStatusEnum.SEND_MAIL_SUCCESS.name());
                order.setIsSendEmail(true);
            } catch (MessagingException e) {
                log.error("Send email error ORDER " + order.getId(), e);
                order.setOrderStatus(OrderStatusEnum.SEND_MAIL_FAIL.name());
                order.setIsSendEmail(false);
            }
            orderRepository.save(order);

            log.info("END... Email sent success order: " + order );
        }
    }
}
