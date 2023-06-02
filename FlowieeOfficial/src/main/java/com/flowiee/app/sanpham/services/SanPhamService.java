package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.SanPham;

import java.util.List;

public interface SanPhamService {

    List<SanPham> findAll();

    SanPham findById(int productID);

    String save(SanPham sanPham);

    String update(SanPham sanPham, int id);

    String delete(int productID);
}