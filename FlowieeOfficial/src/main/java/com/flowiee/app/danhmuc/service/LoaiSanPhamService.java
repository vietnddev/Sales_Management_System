package com.flowiee.app.danhmuc.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.danhmuc.entity.LoaiSanPham;
import com.flowiee.app.danhmuc.repository.LoaiSanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LoaiSanPhamService extends BaseService<LoaiSanPham> {
    LoaiSanPham findByTen(String tenLoai);

    String importData(MultipartFile fileImport);

    byte[] exportTemplate();

    byte[] exportData();
}