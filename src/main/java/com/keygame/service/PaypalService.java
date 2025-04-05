package com.keygame.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.keygame.common.PaypalPaymentIntentEnum;
import com.keygame.common.PaypalPaymentMethodEnum;

import java.math.BigDecimal;

public interface PaypalService {
    Payment createPayment(
            BigDecimal total,
            String currency,
            PaypalPaymentMethodEnum method,
            PaypalPaymentIntentEnum intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
}
