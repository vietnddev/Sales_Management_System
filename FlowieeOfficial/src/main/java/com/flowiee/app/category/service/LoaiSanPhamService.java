package com.flowiee.app.category.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.category.entity.LoaiSanPham;
import org.springframework.web.multipart.MultipartFile;

public interface LoaiSanPhamService extends BaseService<LoaiSanPham> {
    LoaiSanPham findByTen(String tenLoai);

    String importData(MultipartFile fileImport);

    byte[] exportTemplate();

    byte[] exportData();
}