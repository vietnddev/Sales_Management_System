package com.flowiee.app.sanpham.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.sanpham.entity.GoodsImport;
import com.flowiee.app.sanpham.model.GoodsImportRequest;

import java.util.List;

public interface GoodsImportService extends BaseService<GoodsImport> {
    String saveDraft(GoodsImportRequest goodsImportRequest);

    List<GoodsImport> search(String text, Integer supplierId, Integer paymentMethod, String payStatus, String importStatus);

    List<GoodsImport> findByMaterialId(Integer materialId);

    List<GoodsImport> findBySupplierId(Integer supplierId);

    List<GoodsImport> findByPaymentMethod(String paymentMethod);

    List<GoodsImport> findByPaidStatus(String paidStatus);

    List<GoodsImport> findByAccountId(Integer accountId);

    GoodsImport findDraftImportPresent(Integer createdBy);

    GoodsImport createDraftImport();

    String updateStatus(Integer entityId, String status);
}