package com.flowiee.pms.service.product;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.entity.product.MaterialHistory;

import java.util.List;

public interface MaterialHistoryService extends BaseService<MaterialHistory> {
    List<MaterialHistory> findByMaterialId(Integer materialId);

    List<MaterialHistory> findByFieldName(String action);
}