package com.keygame.controller;

import com.keygame.config.ResponseConfig;
import com.keygame.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/cancel-payment")
    public ResponseEntity cancelPay(@RequestParam("orderId") Long orderId) {
        return ResponseConfig.success(orderService.cancelOrder(orderId));
    }

    @GetMapping("/order/payment")
    public ResponseEntity successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        return ResponseConfig.success(orderService.executePayment(paymentId, payerId));
    }
}
