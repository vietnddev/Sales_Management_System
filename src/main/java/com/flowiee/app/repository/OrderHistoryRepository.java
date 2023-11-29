package com.flowiee.app.repository;

import com.flowiee.app.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer> {
    @Query("from OrderHistory o where o.order.id=:orderId")
    List<OrderHistory> findByOrderId(Integer orderId);

    @Query("from OrderHistory o where o.orderDetail.id=:orderDetailId")
    List<OrderHistory> findByOrderDetailId(Integer orderDetailId);

    @Query("from OrderHistory o where o.orderPay.id=:orderPayId")
    List<OrderHistory> findByOrderPayId(Integer orderPayId);
}