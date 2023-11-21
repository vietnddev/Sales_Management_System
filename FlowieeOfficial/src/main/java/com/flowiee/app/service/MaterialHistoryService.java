package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.MaterialHistory;

import java.util.List;

public interface MaterialHistoryService extends BaseService<MaterialHistory> {
    List<MaterialHistory> findByMaterialId(Integer materialId);

    List<MaterialHistory> findByAction(String action);
}