package com.keygame.dto.response;

import com.keygame.common.OrderStatusEnum;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private String userEmail;
    private BigDecimal subPrice;
    private BigDecimal paymentFee;
    private BigDecimal totalPrice;
    private OrderStatusEnum orderStatus;
    private List<OrderDetailDto> orderDetailDtos;
}
