package com.flowiee.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.OrderPay;

import java.util.List;

@Repository
public interface OrderPayRepository extends JpaRepository<OrderPay, Integer> {
    @Query("from OrderPay d where d.order.id=:id")
    List<OrderPay> findByDonHangId(int id);
}