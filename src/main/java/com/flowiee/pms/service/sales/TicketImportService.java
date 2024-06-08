package com.flowiee.pms.service.sales;

import com.flowiee.pms.model.dto.TicketImportDTO;
import com.flowiee.pms.service.BaseCurdService;
import com.flowiee.pms.entity.product.MaterialTemp;
import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.entity.sales.TicketImport;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TicketImportService extends BaseCurdService<TicketImport> {
    Page<TicketImport> findAll(int pageSize, int pageNum, String text, Integer supplierId, Integer paymentMethod, String payStatus, String importStatus, Integer storageId);

    TicketImport findDraftImportPresent(Integer createdBy);

    TicketImport createDraftTicketImport(TicketImportDTO ticketImportDTO);

    TicketImport updateStatus(Integer entityId, String status);

    List<ProductVariantTemp> addProductToTicket(Integer ticketImportId, List<Integer> productVariantIds);

    List<MaterialTemp> addMaterialToTicket(Integer ticketImportId, List<Integer> materialIds);
}