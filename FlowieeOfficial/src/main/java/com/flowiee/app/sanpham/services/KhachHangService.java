package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.KhachHang;

import java.util.List;

public interface KhachHangService {
    List<KhachHang> findAll();

    KhachHang findById(int id);
}