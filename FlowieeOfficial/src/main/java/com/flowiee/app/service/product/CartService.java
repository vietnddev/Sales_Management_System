package com.flowiee.app.service.product;

import java.util.List;

import com.flowiee.app.entity.OrderCart;

public interface CartService {
    List<OrderCart> findAll();

    List<OrderCart> findByAccountId(Integer accountId);

    OrderCart findById(int id);

    String save(OrderCart orderCart);

    String delete(int id);
}