package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> findAll();

    List<Cart> findByAccountId(int accountId);

    Cart findById(int id);

    String save(Cart cart);

    String delete(int id);
}