package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.model.DonHangRequest;

import java.util.List;

public interface DonHangService {
    List<DonHang> findAll();

    List<DonHang> findAll(String searchTxt, String thoiGianDatHang, int kenhBanHangId, int hinhThucThanhToanId, int trangThaiDonHangId);

    List<DonHang> findByTrangThai(int trangThaiDonHangId);

    List<DonHang> search();

    DonHang findById(int id);

    DonHang save(DonHangRequest request);

    DonHang update(DonHang donHang, int id);

    String delete(int id);
}