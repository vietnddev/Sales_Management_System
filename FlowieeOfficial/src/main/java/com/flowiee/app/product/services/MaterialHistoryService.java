package com.flowiee.app.product.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.product.entity.MaterialHistory;

import java.util.List;

public interface MaterialHistoryService extends BaseService<MaterialHistory> {
    List<MaterialHistory> findByMaterialId(Integer materialId);

    List<MaterialHistory> findByAction(String action);
}