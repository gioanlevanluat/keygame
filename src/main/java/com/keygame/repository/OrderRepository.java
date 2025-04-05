package com.keygame.repository;

import com.keygame.dto.request.ReportOrderDto;
import com.keygame.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long> {

    @Query(value = "select o from Order o where o.paymentId = :paymentId and o.isDeleted = false")
    Order findByPaymentId(String paymentId);

    @Query(value = "select o from Order o where o.userId = :userId and o.isDeleted = false order by o.createdAt desc")
    List<Order> findAllByUserId(Long userId);

    @Query(value = "select * from orders o where o.created_at >= current_date - interval '1 hour' " +
            "and order_status = 'PAYMENT_PROCESS' and is_deleted = false", nativeQuery = true)
    List<Order> findOrderPaymentProcessing();

    @Query(value = "select * from orders o where o.created_at >= current_date - interval '1 hour'" +
            " and (order_status = 'SEND_MAIL_FAIL' or  order_status = 'GET_KEY_SUCCESS')" +
            " and is_deleted = false", nativeQuery = true)
    List<Order> findOrderSuccess();

    @Query(value = "select o from Order o where (:#{#reportOrderDto.isSendEmail} is null or (:#{#reportOrderDto.isSendEmail} = true and o.isSendEmail = true) or o.isSendEmail = false or o.isSendEmail is null) " +
            " and (:#{#reportOrderDto.orderStatus} is null or o.orderStatus in :#{#reportOrderDto.orderStatus}) " +
            " and o.createdAt between :#{#reportOrderDto.beginCreateAt} and :#{#reportOrderDto.endCreatedAt} " +
            " order by o.createdAt desc")
    List<Order> searchOrder(ReportOrderDto reportOrderDto);
}
