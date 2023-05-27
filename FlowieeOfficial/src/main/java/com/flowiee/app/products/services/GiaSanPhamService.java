package com.flowiee.app.products.services;

import com.flowiee.app.products.entity.GiaSanPham;

import java.util.List;

public interface GiaSanPhamService {

    void save(GiaSanPham priceHistory);

    List<GiaSanPham> getListPriceByPVariantID(int pVariantID);
}