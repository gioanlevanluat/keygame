package com.keygame.service;

import com.keygame.dto.request.CreateOrderDto;
import com.keygame.dto.request.ReportOrderDto;
import com.keygame.dto.response.OrderDto;
import com.keygame.dto.response.ResponseReportOrderDto;
import com.keygame.entity.Order;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface OrderService {

    String save(CreateOrderDto orderDto, HttpServletRequest req);

    List<OrderDto> findAll(HttpServletRequest request);

    boolean executePayment(String paymentId, String payerId);

    boolean cancelOrder(Long orderId);

    boolean completeOrder(Order order);

    ResponseReportOrderDto searchOrder(ReportOrderDto reportOrderDto);
}
