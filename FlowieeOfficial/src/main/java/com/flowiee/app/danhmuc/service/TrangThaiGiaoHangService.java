package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.TrangThaiGiaoHang;

import java.util.List;

public interface TrangThaiGiaoHangService {
    List<TrangThaiGiaoHang> findAll();

    List<TrangThaiGiaoHang> findByDonHangId(int donHangId);

    TrangThaiGiaoHang findById(int id);

    String save(TrangThaiGiaoHang trangThaiGiaoHang);

    String update(TrangThaiGiaoHang trangThaiGiaoHang, int id);

    String delete(int id);
}