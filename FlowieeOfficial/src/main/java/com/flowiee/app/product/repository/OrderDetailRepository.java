package com.flowiee.app.product.repository;

import com.flowiee.app.product.entity.Order;
import com.flowiee.app.product.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query("from OrderDetail d where d.order=:orderId")
    List<OrderDetail> findByDonHangId(Order orderId);
}