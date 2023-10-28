package com.flowiee.app.sanpham.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.storage.entity.MaterialTemp;

import java.util.List;

public interface MaterialTempService extends BaseService<MaterialTemp> {
    List<MaterialTemp> findByCode(String code);

    List<MaterialTemp> findByImportId(Integer importId);
}