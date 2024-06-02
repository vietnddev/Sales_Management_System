package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.sales.LedgerPayment;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.service.sales.LedgerPaymentService;
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
@RequestMapping("${app.api.prefix}/ledger")
@Tag(name = "LedgerPayment API", description = "Quản lý phiếu chi")
public class GeneralLedgerController extends BaseController {
    @Autowired
    private LedgerPaymentService ledgerPaymentService;

    @Operation(summary = "Find general ledger")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleSales.readGeneralLedger(true)")
    public AppResponse<List<LedgerPayment>> findGeneralLedger(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                              @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            Page<LedgerPayment> ledgerPayments = ledgerPaymentService.findAll(pageSize, pageNum - 1);
            return success(ledgerPayments.getContent(), pageNum, pageSize, ledgerPayments.getTotalPages(), ledgerPayments.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "ledgerPayment"), ex);
        }
    }
}