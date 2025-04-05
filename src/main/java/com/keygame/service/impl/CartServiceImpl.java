package com.keygame.service.impl;

import com.keygame.config.exceptionhandler.BusinessException;
import com.keygame.dto.CartDto;
import com.keygame.dto.CartItemDto;
import com.keygame.dto.response.CartInfoDto;
import com.keygame.dto.response.ProductInfoDto;
import com.keygame.entity.PaymentMethod;
import com.keygame.entity.Product;
import com.keygame.repository.PaymentMethodRepository;
import com.keygame.repository.ProductRepository;
import com.keygame.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public static final String PRODUCT_ERROR = "There is a invalid product in the cart. Please contact us for processing.";
    public static final String PAYMENT_METHOD_NOT_EXIST = "The current payment method is not working. " +
            "Please choose another payment method.";

    @Override
    public CartInfoDto getCartInfo(CartDto cartDto) {

        if (CollectionUtils.isEmpty(cartDto.getCartItems())) {
            CartInfoDto cartInfoDto = new CartInfoDto();
            cartInfoDto.setTotalPrice(BigDecimal.ZERO);
            cartInfoDto.setSubPrice(BigDecimal.ZERO);
            cartInfoDto.setPaymentFee(BigDecimal.ZERO);
            return cartInfoDto;
        }

        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(cartDto.getPaymentMethod());
        if (!paymentMethod.isPresent()) {
            throw BusinessException.builder().message(PAYMENT_METHOD_NOT_EXIST).build();
        }

        BigDecimal paymentFee = BigDecimal.ZERO;
        BigDecimal subPrice = BigDecimal.ZERO;
        List<Product> products = new ArrayList<>();
        for (CartItemDto cartItemDto : cartDto.getCartItems()) {
            if (cartItemDto.getProductId() == null || cartItemDto.getProductId() < 1 || cartItemDto.getQuantity() < 1) {
                throw BusinessException.builder().message(PRODUCT_ERROR).build();
            }
            Optional<Product> product = productRepository.findById(cartItemDto.getProductId());
            if (!product.isPresent() || product.get().isDeleted()) {
                throw BusinessException.builder().message(PRODUCT_ERROR).build();
            }
            paymentFee = paymentFee.add(calculatePaymentFee(paymentMethod.get(), product.get().getPrice()));
            subPrice = subPrice.add(product.get().getPrice());
            products.add(product.get());
        }

        CartInfoDto cartInfoDto = new CartInfoDto();
        cartInfoDto.setSubPrice(subPrice.setScale(2, RoundingMode.CEILING));
        cartInfoDto.setPaymentFee(paymentFee.setScale(2, RoundingMode.CEILING));
        cartInfoDto.setTotalPrice(cartInfoDto.getSubPrice().add(cartInfoDto.getPaymentFee()));
        cartInfoDto.setProducts(products);
        return cartInfoDto;
    }

    private BigDecimal calculatePaymentFee(PaymentMethod paymentMethod, BigDecimal productPrice) {

//        if (!paymentMethod.getIsDynamic()) {
        return paymentMethod.getFixedFee().add(productPrice.multiply(paymentMethod.getPercentFee().subtract(BigDecimal.valueOf(100))));
//        }
    }
}
