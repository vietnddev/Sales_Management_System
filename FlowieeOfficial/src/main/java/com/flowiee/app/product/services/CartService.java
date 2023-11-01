package com.flowiee.app.product.services;

import com.flowiee.app.product.entity.OrderCart;

import java.util.List;

public interface CartService {
    List<OrderCart> findAll();

    List<OrderCart> findByAccountId(int accountId);

    OrderCart findById(int id);

    String save(OrderCart orderCart);

    String delete(int id);
}