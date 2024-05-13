package com.flowiee.pms.service.product;

import com.flowiee.pms.service.CrudService;
import com.flowiee.pms.entity.product.Material;
import org.springframework.data.domain.Page;

public interface  MaterialService extends CrudService<Material> {
    Page<Material> findAll(int pageSize, int pageNum, Integer supplierId, Integer unitId, String code, String name, String location, String status);

    void updateQuantity(Integer quantity, Integer materialId, String type);
}