package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.GiaSanPham;

import java.util.List;

public interface GiaSanPhamService {

    void save(GiaSanPham priceHistory);

    List<GiaSanPham> getListPriceByPVariantID(BienTheSanPham pVariantID);
}