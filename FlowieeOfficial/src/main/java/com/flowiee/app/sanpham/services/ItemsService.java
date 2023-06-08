package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.Cart;
import com.flowiee.app.sanpham.entity.Items;

import java.util.List;

public interface ItemsService {
    List<Items> findAll();

    List<Items> findByCartId(int cartId);

    Items findById(int id);

    String save(Items cart);

    String delete(int id);
}
