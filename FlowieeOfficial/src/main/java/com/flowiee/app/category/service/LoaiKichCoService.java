package com.flowiee.app.category.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.category.entity.LoaiKichCo;
import org.springframework.web.multipart.MultipartFile;

public interface LoaiKichCoService extends BaseService<LoaiKichCo> {
    String importData(MultipartFile fileImport);

    byte[] exportTemplate();

    byte[] exportData();
}