package com.flowiee.app.service;

import java.util.List;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Price;

public interface PriceService extends BaseService<Price> {
    List<Price> findPricesByProductVariant(int bienTheSanPhamId);

    Double findGiaHienTai(int bienTheSanPhamId);

    String update(Price price, int bienTheSanPhamId, int giaSanPhamId);
}