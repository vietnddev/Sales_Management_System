package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.DonHangChiTiet;
import com.flowiee.app.sanpham.model.DonHangChiTietResponse;

import java.util.List;

public interface ChiTietDonHangService {
    List<DonHangChiTiet> findAll();

    DonHangChiTiet findById(int id);

    List<DonHangChiTiet> findByDonHangId(int donHangId);

    String save(DonHangChiTiet donHangChiTiet);

    String update(DonHangChiTiet donHangChiTiet, int id);

    String delete(int id);

    List<DonHangChiTietResponse> convertToDonHangChiTietResponse(List<DonHangChiTiet> listDonHangChiTiet);
}