package com.flowiee.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.Order;
import com.flowiee.app.entity.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query("from OrderDetail d where d.order=:orderId")
    List<OrderDetail> findByDonHangId(Order orderId);
}