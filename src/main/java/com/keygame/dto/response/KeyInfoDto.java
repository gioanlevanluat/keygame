package com.keygame.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyInfoDto {
    private Long id;
    private String code;
    private Long productId;
    private Long orderDetailId;
    private Long orderId;
    private Boolean isOrdered;
    private Boolean orderDate;
    private Date createdDate;
    private BigDecimal basePrice;
    private String supplier;
    private Boolean isDeleted;
}
