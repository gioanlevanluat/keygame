package com.keygame.repository;

import com.keygame.dto.request.ReportOrderDetailDto;
import com.keygame.dto.request.ReportOrderDto;
import com.keygame.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrderId(Long orderId);

    @Query(value = "select od from OrderDetail od join Order o on o.id = od.orderId where o.isDeleted = false and o.isDeleted = false " +
            " and (:#{#reportOrderDetailDto.productId} is null or od.productId in :#{#reportOrderDetailDto.productId}) " +
            " and (:#{#reportOrderDetailDto.key} is null or od.key = :#{#reportOrderDetailDto.key}) " +
            " and (:#{#reportOrderDetailDto.orderId} is null or od.orderId = :#{#reportOrderDetailDto.orderId}) " +
            " and (:#{#reportOrderDetailDto.outOfKeys} is null or od.outOfKeys = :#{#reportOrderDetailDto.outOfKeys}) " +
            " and o.createdAt between :#{#reportOrderDetailDto.beginCreateAt} and :#{#reportOrderDetailDto.endCreatedAt} ")
    List<OrderDetail> searchOrderDetail(ReportOrderDetailDto reportOrderDetailDto);
}
