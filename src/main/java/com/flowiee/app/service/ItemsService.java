package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Items;

import java.util.List;

public interface ItemsService extends BaseService<Items> {
    List<Items> findAll();

    List<Items> findByCartId(Integer cartId);

    Integer findSoLuongByBienTheSanPhamId(int bienTheSanPhamId);
}