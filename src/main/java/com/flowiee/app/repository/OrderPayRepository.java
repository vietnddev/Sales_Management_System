package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.OrderPay;

import java.util.List;

@Repository
public interface OrderPayRepository extends JpaRepository<OrderPay, Integer> {
    @Query("from OrderPay d where d.order.id=:id")
    List<OrderPay> findByOrder(@Param("id") Integer id);

    @Query("from OrderPay o where o.hinhThucThanhToan.id=:payMethodId")
    List<OrderPay> findByPayMethod(@Param("payMethodId") Integer payMethodId);
}