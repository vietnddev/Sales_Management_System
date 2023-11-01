package com.flowiee.app.product.repository;

import com.flowiee.app.product.entity.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderCartRepository extends JpaRepository<OrderCart, Integer> {
    @Query("from OrderCart c where c.createdBy=:createdBy")
    List<OrderCart> findByAccountId(String createdBy);
}