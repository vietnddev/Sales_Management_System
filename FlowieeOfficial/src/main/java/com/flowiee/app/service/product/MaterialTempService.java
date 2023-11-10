package com.flowiee.app.service.product;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.MaterialTemp;

import java.util.List;

public interface MaterialTempService extends BaseService<MaterialTemp> {
    List<MaterialTemp> findByImportId(Integer importId);
    
    MaterialTemp findMaterialInGoodsImport(Integer importId, Integer materialId);
}