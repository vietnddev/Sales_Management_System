package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.TrangThaiDonHang;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TrangThaiDonHangService {
    List<TrangThaiDonHang> findAll();

    TrangThaiDonHang findById(int id);

    String save(TrangThaiDonHang trangThaiDonHang);

    String update(TrangThaiDonHang trangThaiDonHang, int id);

    String delete(int id);

    String importData(MultipartFile fileImport);

    byte[] exportData();
}