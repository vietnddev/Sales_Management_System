package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.DonHangThanhToan;

import java.util.List;

public interface DonHangThanhToanService {
    List<DonHangThanhToan> findAll();

    List<DonHangThanhToan> findByDonHangId(int id);

    DonHangThanhToan findById(int id);

    String save(DonHangThanhToan donHangThanhToan);

    String update(DonHangThanhToan donHangThanhToan, int id);

    String delete(int id);
}