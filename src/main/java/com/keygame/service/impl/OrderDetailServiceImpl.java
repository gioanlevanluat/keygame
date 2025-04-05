package com.keygame.service.impl;

import com.keygame.dto.request.ReportOrderDetailDto;
import com.keygame.dto.response.ResponseReportOrderDetailDto;
import com.keygame.entity.OrderDetail;
import com.keygame.repository.OrderDetailRepository;
import com.keygame.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public ResponseReportOrderDetailDto searchOrderDetail(ReportOrderDetailDto reportOrderDetailDto) {
        ResponseReportOrderDetailDto response = new ResponseReportOrderDetailDto();

        List<OrderDetail> orderDetails = orderDetailRepository.searchOrderDetail(reportOrderDetailDto);
        response.setOrderDetails(orderDetails);
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalBasePrice = BigDecimal.ZERO;
        BigDecimal totalProfit = BigDecimal.ZERO;
        for (OrderDetail orderDetail : orderDetails) {
            totalPrice = totalPrice.add(orderDetail.getPrice());
            totalBasePrice = totalBasePrice.add(orderDetail.getBasePrice());
            totalProfit = totalProfit.add(orderDetail.getPrice().subtract(orderDetail.getBasePrice()));
        }
        response.setTotalPrice(totalPrice);
        response.setTotalBasePrice(totalBasePrice);
        response.setTotalProfit(totalProfit);
        return response;
    }
}
