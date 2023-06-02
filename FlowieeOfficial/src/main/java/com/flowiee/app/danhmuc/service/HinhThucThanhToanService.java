package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.HinhThucThanhToan;

import java.util.List;

public interface HinhThucThanhToanService {
    List<HinhThucThanhToan> findAll();

    HinhThucThanhToan findById(int id);

    String save(HinhThucThanhToan loaiMauSac);

    String update(HinhThucThanhToan loaiMauSac, int id);

    String delete(int id);
}