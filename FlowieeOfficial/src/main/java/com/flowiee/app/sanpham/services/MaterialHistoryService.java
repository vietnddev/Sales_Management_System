package com.flowiee.app.sanpham.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.sanpham.entity.MaterialHistory;

import java.util.List;

public interface MaterialHistoryService extends BaseService<MaterialHistory> {
    List<MaterialHistory> findByMaterialId(Integer materialId);

    List<MaterialHistory> findByAction(String action);
}