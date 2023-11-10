package com.flowiee.app.service.product;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Items;

import java.util.List;

public interface ItemsService extends BaseService<Items> {
    List<Items> findByCartId(Integer cartId);

    Integer findSoLuongByBienTheSanPhamId(int bienTheSanPhamId);
}