package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.TicketImport;
import com.flowiee.app.model.request.TicketImportGoodsRequest;

import java.util.List;

public interface TicketImportGoodsService extends BaseService<TicketImport> {
    List<TicketImport> findAll();

    List<TicketImport> findAll(String text, Integer supplierId, Integer paymentMethod, String payStatus, String importStatus);

    TicketImport saveDraft(TicketImportGoodsRequest ticketImportGoodsRequest);

    //List<TicketImportGoods> search(String text, Integer supplierId, Integer paymentMethod, String payStatus, String importStatus);

    List<TicketImport> findByMaterialId(Integer materialId);

    List<TicketImport> findBySupplierId(Integer supplierId);

    List<TicketImport> findByPaymentMethod(String paymentMethod);

    List<TicketImport> findByPaidStatus(String paidStatus);

    List<TicketImport> findByAccountId(Integer accountId);

    TicketImport findDraftImportPresent(Integer createdBy);

    TicketImport createDraftImport();

    TicketImport updateStatus(Integer entityId, String status);
}