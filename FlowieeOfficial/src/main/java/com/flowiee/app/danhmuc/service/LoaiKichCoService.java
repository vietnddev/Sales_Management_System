package com.flowiee.app.danhmuc.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.danhmuc.entity.LoaiKichCo;
import org.springframework.web.multipart.MultipartFile;

public interface LoaiKichCoService extends BaseService<LoaiKichCo> {
    String importData(MultipartFile fileImport);

    byte[] exportTemplate();

    byte[] exportData();
}