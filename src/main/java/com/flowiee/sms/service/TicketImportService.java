package com.flowiee.sms.service;

import com.flowiee.sms.core.BaseService;
import com.flowiee.sms.entity.MaterialTemp;
import com.flowiee.sms.entity.ProductVariantTemp;
import com.flowiee.sms.entity.TicketImport;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TicketImportService extends BaseService<TicketImport> {
    List<TicketImport> findAll();

    Page<TicketImport> findAll(int pageSize, int pageNum);

    List<TicketImport> findAll(String text, Integer supplierId, Integer paymentMethod, String payStatus, String importStatus);

    TicketImport findDraftImportPresent(Integer createdBy);

    TicketImport createDraftTicketImport(String title);

    TicketImport updateStatus(Integer entityId, String status);

    List<ProductVariantTemp> addProductToTicket(Integer ticketImportId, List<Integer> productVariantIds);

    List<MaterialTemp> addMaterialToTicket(Integer ticketImportId, List<Integer> materialIds);
}