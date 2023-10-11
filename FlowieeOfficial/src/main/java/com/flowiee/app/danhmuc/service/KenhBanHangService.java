package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.KenhBanHang;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface KenhBanHangService {
    List<KenhBanHang> findAll();

    KenhBanHang findById(int id);

    String save(KenhBanHang loaiMauSac);

    String update(KenhBanHang loaiMauSac, int id);

    String delete(int id);

    String importData(MultipartFile fileImport);

    byte[] exportTemplate();

    byte[] exportData();
}