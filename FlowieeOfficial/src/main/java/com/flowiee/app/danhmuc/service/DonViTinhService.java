package com.flowiee.app.danhmuc.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.danhmuc.entity.DonViTinh;
import org.springframework.web.multipart.MultipartFile;


public interface DonViTinhService extends BaseService<DonViTinh> {
    String importData(MultipartFile fileImport);

    byte[] exportTemplate();

    byte[] exportData();
}