package com.flowiee.app.service.product;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.TicketImportGoods;
import com.flowiee.app.model.product.GoodsImportRequest;

import java.util.List;

public interface GoodsImportService extends BaseService<TicketImportGoods> {
    String saveDraft(GoodsImportRequest goodsImportRequest);

    List<TicketImportGoods> search(String text, Integer supplierId, Integer paymentMethod, String payStatus, String importStatus);

    List<TicketImportGoods> findByMaterialId(Integer materialId);

    List<TicketImportGoods> findBySupplierId(Integer supplierId);

    List<TicketImportGoods> findByPaymentMethod(String paymentMethod);

    List<TicketImportGoods> findByPaidStatus(String paidStatus);

    List<TicketImportGoods> findByAccountId(Integer accountId);

    TicketImportGoods findDraftImportPresent(Integer createdBy);

    TicketImportGoods createDraftImport();

    String updateStatus(Integer entityId, String status);
}