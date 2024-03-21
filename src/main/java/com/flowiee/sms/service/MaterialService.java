package com.flowiee.sms.service;

import com.flowiee.sms.core.BaseService;
import com.flowiee.sms.entity.Material;
import org.springframework.data.domain.Page;

import java.util.List;

public interface  MaterialService extends BaseService<Material> {
    Page<Material> findAll(Integer pageSize, Integer pageNum);

    List<Material> findAll(Integer ticketImportId, Integer supplierId, Integer unitId, String code, String name, String location, String status);

    List<Material> findByCode(String code);

    List<Material> findByImportId(Integer importId);

    List<Material> findByUnit(Integer unitId);

    void updateQuantity(Integer quantity, Integer materialId, String type);
}