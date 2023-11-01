package com.flowiee.app.category.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.category.entity.HinhThucThanhToan;
import org.springframework.web.multipart.MultipartFile;

public interface HinhThucThanhToanService extends BaseService<HinhThucThanhToan> {
    String importData(MultipartFile fileImport);

    byte[] exportTemplate();

    byte[] exportData();
}