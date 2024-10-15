package com.flowiee.pms.repository.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.pms.entity.sales.OrderCart;

import java.util.List;

@Repository
public interface OrderCartRepository extends JpaRepository<OrderCart, Long> {
    @Query("from OrderCart c where c.createdBy=:createdBy")
    List<OrderCart> findByAccountId(@Param("createdBy") Long createdBy);
}