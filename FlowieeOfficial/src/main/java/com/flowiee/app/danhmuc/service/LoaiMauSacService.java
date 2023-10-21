package com.flowiee.app.danhmuc.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.danhmuc.entity.LoaiMauSac;
import org.springframework.web.multipart.MultipartFile;

public interface LoaiMauSacService extends BaseService<LoaiMauSac> {
    String importData(MultipartFile fileImport);

    byte[] exportTemplate();

    byte[] exportData();
}