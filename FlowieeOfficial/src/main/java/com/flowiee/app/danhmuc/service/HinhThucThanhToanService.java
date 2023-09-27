package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.HinhThucThanhToan;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HinhThucThanhToanService {
    List<HinhThucThanhToan> findAll();

    HinhThucThanhToan findById(int id);

    String save(HinhThucThanhToan loaiMauSac);

    String update(HinhThucThanhToan loaiMauSac, int id);

    String delete(int id);

    String importData(MultipartFile fileImport);

    byte[] exportData();
}