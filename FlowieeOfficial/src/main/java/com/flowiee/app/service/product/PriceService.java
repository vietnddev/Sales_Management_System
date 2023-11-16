package com.flowiee.app.service.product;

import java.util.List;

import com.flowiee.app.entity.Price;

public interface PriceService {
    List<Price> findAll();

    List<Price> findPricesByProductVariant(int bienTheSanPhamId);

    Price findById(int id);

    Double findGiaHienTai(int bienTheSanPhamId);

    String save(Price price);

    String update(Price price, int bienTheSanPhamId, int giaSanPhamId);

    String delete(int id);
}