package com.flowiee.app.category.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.category.entity.LoaiMauSac;
import org.springframework.web.multipart.MultipartFile;

public interface LoaiMauSacService extends BaseService<LoaiMauSac> {
    String importData(MultipartFile fileImport);

    byte[] exportTemplate();

    byte[] exportData();
}