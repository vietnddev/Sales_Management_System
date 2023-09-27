package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.danhmuc.repository.LoaiTaiLieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LoaiTaiLieuService {

    List<LoaiTaiLieu> findAll();

    List<LoaiTaiLieu> findAllWhereStatusTrue();

    LoaiTaiLieu findById(Integer id);

    LoaiTaiLieu findByTen(String ten);

    LoaiTaiLieu findDocTypeDefault();

    LoaiTaiLieu save(LoaiTaiLieu loaiTaiLieu);

    void update(LoaiTaiLieu loaiSanPham, Integer id);

    boolean delete(Integer id);

    String importData(MultipartFile fileImport);

    byte[] exportData();
}