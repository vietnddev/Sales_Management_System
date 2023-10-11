package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.LoaiSanPham;
import com.flowiee.app.danhmuc.repository.LoaiSanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LoaiSanPhamService {

    List<LoaiSanPham> findAll();

    LoaiSanPham findById(int id);

    LoaiSanPham findByTen(String tenLoai);

    LoaiSanPham save(LoaiSanPham loaiSanPham);

    void update(LoaiSanPham loaiSanPham, int id);

    boolean delete(int id);

    String importData(MultipartFile fileImport);

    byte[] exportTemplate();

    byte[] exportData();
}