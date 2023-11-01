package com.flowiee.app.product.services;

import com.flowiee.app.product.entity.Price;

import java.util.List;

public interface PriceService {
    List<Price> findAll();

    List<Price> findByBienTheSanPhamId(int bienTheSanPhamId);

    Price findById(int id);

    Double findGiaHienTai(int bienTheSanPhamId);

    Price findGiaHienTaiModel(int bienTheSanPhamId);

    String save(Price price);

    String update(Price price, int bienTheSanPhamId, int giaSanPhamId);

    String delete(int id);
}