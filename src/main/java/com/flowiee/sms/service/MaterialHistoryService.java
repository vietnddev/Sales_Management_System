package com.flowiee.sms.service;

import com.flowiee.sms.core.BaseService;
import com.flowiee.sms.entity.MaterialHistory;

import java.util.List;

public interface MaterialHistoryService extends BaseService<MaterialHistory> {
    List<MaterialHistory> findAll();

    List<MaterialHistory> findByMaterialId(Integer materialId);

    List<MaterialHistory> findByFieldName(String action);
}