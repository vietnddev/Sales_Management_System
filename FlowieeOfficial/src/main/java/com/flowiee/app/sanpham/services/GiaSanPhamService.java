package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.GiaSanPham;

import java.util.List;

public interface GiaSanPhamService {
    List<GiaSanPham> findAll();

    List<GiaSanPham> findByBienTheSanPhamId(int bienTheSanPhamId);

    GiaSanPham findById(int id);

    Double findGiaHienTai(int bienTheSanPhamId);

    String save(GiaSanPham giaSanPham);

    String update(GiaSanPham giaSanPham, int id);

    String delete(int id);
}