package com.flowiee.app.service.product;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.GoodsImport;
import com.flowiee.app.model.product.GoodsImportRequest;

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