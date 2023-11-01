package com.flowiee.app.category.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.category.entity.KenhBanHang;
import org.springframework.web.multipart.MultipartFile;

public interface KenhBanHangService extends BaseService<KenhBanHang> {
    String importData(MultipartFile fileImport);

    byte[] exportTemplate();

    byte[] exportData();
}