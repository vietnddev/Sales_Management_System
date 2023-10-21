package com.flowiee.app.danhmuc.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.danhmuc.entity.KenhBanHang;
import org.springframework.web.multipart.MultipartFile;

public interface KenhBanHangService extends BaseService<KenhBanHang> {
    String importData(MultipartFile fileImport);

    byte[] exportTemplate();

    byte[] exportData();
}