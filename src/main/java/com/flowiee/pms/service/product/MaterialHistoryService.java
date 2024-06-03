package com.flowiee.pms.service.product;

import com.flowiee.pms.service.BaseCurd;
import com.flowiee.pms.entity.product.MaterialHistory;

import java.util.List;
import java.util.Map;

public interface MaterialHistoryService extends BaseCurd<MaterialHistory> {
    List<MaterialHistory> findByMaterialId(Integer materialId);

    List<MaterialHistory> findByFieldName(String action);

    List<MaterialHistory> save(Map<String, Object[]> logChanges, String title, Integer materialId);
}