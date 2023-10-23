package com.flowiee.app.sanpham.services;

import com.flowiee.app.sanpham.entity.MaterialImport;

import java.util.List;

public interface MaterialImportService {
    List<MaterialImport> search(Integer materialId, Integer supplierId);

    List<MaterialImport> findByMaterialId(Integer materialId);

    List<MaterialImport> findBySupplierId(Integer supplierId);

    List<MaterialImport> findByPaymentMethod(String paymentMethod);

    List<MaterialImport> findByPaidStatus(String paidStatus);

    List<MaterialImport> findByAccountId(Integer accountId);
}