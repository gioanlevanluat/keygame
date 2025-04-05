package com.keygame.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseReportOrderDto {
    private List<OrderFullDto> orders;
    private BigDecimal totalPrice;
    private BigDecimal totalBasePrice;
    private BigDecimal totalProfit;
}