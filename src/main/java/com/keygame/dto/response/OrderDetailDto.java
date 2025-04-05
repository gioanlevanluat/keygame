package com.keygame.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailDto {
    private Long id;
    private Long productId;
    private String productName;
    private String handle;
    private String image;
    private BigDecimal price;
    private BigDecimal paymentFee;
    private BigDecimal basePrice;
    private Long keyId;
    private String key;
    private Long orderId;
}
