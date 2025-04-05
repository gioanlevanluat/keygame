package com.keygame.ultils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PaymentUtils {

    public static BigDecimal autoGenerateProductPrice(BigDecimal productPrice) {
        return productPrice.add(productPrice.multiply(BigDecimal.valueOf(0.04))).setScale(4, RoundingMode.CEILING);
    }

}
