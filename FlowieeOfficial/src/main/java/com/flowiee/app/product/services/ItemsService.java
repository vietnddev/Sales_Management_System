package com.flowiee.app.product.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.product.entity.Items;

import java.util.List;

public interface ItemsService extends BaseService<Items> {
    List<Items> findByCartId(Integer cartId);

    Integer findSoLuongByBienTheSanPhamId(int bienTheSanPhamId);
}