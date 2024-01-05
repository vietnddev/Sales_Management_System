package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.TicketImportGoods;
import com.flowiee.app.model.request.TicketImportGoodsRequest;

import java.util.List;

public interface TicketImportGoodsService extends BaseService<TicketImportGoods> {
    List<TicketImportGoods> findAll();

    List<TicketImportGoods> findAll(String text, Integer supplierId, Integer paymentMethod, String payStatus, String importStatus);

    String saveDraft(TicketImportGoodsRequest ticketImportGoodsRequest);

    //List<TicketImportGoods> search(String text, Integer supplierId, Integer paymentMethod, String payStatus, String importStatus);

    List<TicketImportGoods> findByMaterialId(Integer materialId);

    List<TicketImportGoods> findBySupplierId(Integer supplierId);

    List<TicketImportGoods> findByPaymentMethod(String paymentMethod);

    List<TicketImportGoods> findByPaidStatus(String paidStatus);

    List<TicketImportGoods> findByAccountId(Integer accountId);

    TicketImportGoods findDraftImportPresent(Integer createdBy);

    TicketImportGoods createDraftImport();

    String updateStatus(Integer entityId, String status);
}