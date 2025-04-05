package com.keygame.dto.response;

import com.keygame.entity.OrderDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseReportOrderDetailDto {
    private List<OrderDetail> orderDetails;
    private BigDecimal totalPrice;
    private BigDecimal totalBasePrice;
    private BigDecimal totalProfit;
}
