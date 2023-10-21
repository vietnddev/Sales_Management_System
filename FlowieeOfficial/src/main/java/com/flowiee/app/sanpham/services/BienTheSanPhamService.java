package com.flowiee.app.sanpham.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.sanpham.entity.BienTheSanPham;

import java.util.List;

public interface BienTheSanPhamService extends BaseService<BienTheSanPham> {
    List<BienTheSanPham> getListVariantOfProduct(int sanPhamId);

    Double getGiaBan(int id);

    String updateSoLuong(Integer soLuong, Integer id);
}