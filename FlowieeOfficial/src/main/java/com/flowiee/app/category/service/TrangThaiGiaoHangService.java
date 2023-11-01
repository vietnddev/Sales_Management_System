package com.flowiee.app.category.service;

import com.flowiee.app.category.entity.TrangThaiGiaoHang;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public interface TrangThaiGiaoHangService {
    List<TrangThaiGiaoHang> findAll();

    List<TrangThaiGiaoHang> findByDonHangId(int donHangId);

    TrangThaiGiaoHang findById(int id);

    String save(TrangThaiGiaoHang trangThaiGiaoHang);

    String update(TrangThaiGiaoHang trangThaiGiaoHang, int id);

    String delete(int id);

    String importData(MultipartFile fileImport);

    byte[] exportData();
}