package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.sanpham.entity.Cart;
import com.flowiee.app.sanpham.repository.CartRepository;
import com.flowiee.app.sanpham.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public List<Cart> findByAccountId(int accountId) {
        return cartRepository.findByAccountId(accountId);
    }

    @Override
    public Cart findById(int id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public String save(Cart cart) {
        cartRepository.save(cart);
        return "OK";
    }

    @Override
    public String delete(int id) {
        cartRepository.deleteById(id);
        return "OK";
    }
}
