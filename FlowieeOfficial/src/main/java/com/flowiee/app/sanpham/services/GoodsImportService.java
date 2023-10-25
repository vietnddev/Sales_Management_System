package com.flowiee.app.sanpham.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.sanpham.entity.GoodsImport;

import java.util.List;

public interface GoodsImportService extends BaseService<GoodsImport> {
    List<GoodsImport> search(Integer materialId, Integer supplierId);

    List<GoodsImport> findByMaterialId(Integer materialId);

    List<GoodsImport> findBySupplierId(Integer supplierId);

    List<GoodsImport> findByPaymentMethod(String paymentMethod);

    List<GoodsImport> findByPaidStatus(String paidStatus);

    List<GoodsImport> findByAccountId(Integer accountId);

    GoodsImport findDraftImportPresent(Integer createdBy);

    GoodsImport createDraftImport();

    String updateStatus(Integer entityId, String status);
}