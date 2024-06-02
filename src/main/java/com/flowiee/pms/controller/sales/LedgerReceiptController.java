package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.sales.LedgerReceipt;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.service.sales.LedgerReceiptService;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/ledger-receipt")
@Tag(name = "LedgerReceipt API", description = "Quản lý phiếu thu")
public class LedgerReceiptController extends BaseController {
    @Autowired
    private LedgerReceiptService ledgerReceiptService;

    @Operation(summary = "Find all receipts")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleSales.readLedgerReceipt(true)")
    public AppResponse<List<LedgerReceipt>> findLedgerReceipts(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                               @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            Page<LedgerReceipt> ledgerReceipts = ledgerReceiptService.findAll(pageSize, pageNum - 1);
            return success(ledgerReceipts.getContent(), pageNum, pageSize, ledgerReceipts.getTotalPages(), ledgerReceipts.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "ledgerReceipt"), ex);
        }
    }
}