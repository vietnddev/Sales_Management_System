package com.flowiee.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.product.OrderCart;

import java.util.List;

@Repository
public interface OrderCartRepository extends JpaRepository<OrderCart, Integer> {
    @Query("from OrderCart c where c.createdBy=:createdBy")
    List<OrderCart> findByAccountId(String createdBy);
}