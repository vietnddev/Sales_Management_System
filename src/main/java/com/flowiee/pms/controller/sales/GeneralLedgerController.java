package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.GeneralLedger;
import com.flowiee.pms.service.sales.LedgerService;
import com.flowiee.pms.utils.constants.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("${app.api.prefix}/ledger")
@Tag(name = "LedgerPayment API", description = "Quản lý phiếu chi")
public class GeneralLedgerController extends BaseController {
    private final LedgerService ledgerService;

    public GeneralLedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @Operation(summary = "Find general ledger")
    @GetMapping
    @PreAuthorize("@vldModuleSales.readGeneralLedger(true)")
    public AppResponse<GeneralLedger> findGeneralLedger(@RequestParam("pageSize") Integer pageSize,
                                                        @RequestParam("pageNum") Integer pageNum,
                                                        @RequestParam(value = "fromDate", required = false) LocalDate fromDate,
                                                        @RequestParam(value = "toDate", required = false) LocalDate toDate) {
        try {
            GeneralLedger generalLedger = ledgerService.findGeneralLedger(pageSize, pageNum -1, fromDate, toDate);
            return success(generalLedger, pageNum, pageSize, generalLedger.getTotalPages(), generalLedger.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "generalLedger"), ex);
        }
    }
}