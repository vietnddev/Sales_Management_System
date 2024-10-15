package com.flowiee.pms.repository.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.pms.entity.sales.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("from OrderDetail d where d.order.id=:orderId")
    List<OrderDetail> findByOrderId(@Param("orderId") Long orderId);

    @Query("from OrderDetail d where d.order.id = :orderId and d.productDetail.id = :productVariantId")
    OrderDetail findByOrderIdAndProductVariantId(@Param("orderId") int orderId, @Param("productVariantId") int productVariantId);
}