package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.sales.LedgerReceipt;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.service.sales.LedgerReceiptService;
import com.flowiee.pms.utils.PagesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/ledger/receipt")
public class LedgerReceiptControllerView extends BaseController {
    @Autowired
    private LedgerReceiptService ledgerReceiptService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @PreAuthorize("@vldModuleSales.readLedgerReceipt(true)")
    public ModelAndView getLedgerReceipts() {
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SLS_LEDGER_RECEIPT);
        modelAndView.addObject("ledgerReceipt", new LedgerReceipt());
        modelAndView.addObject("listReceiptGroups", categoryService.findLedgerReceiptGroups());
        modelAndView.addObject("listReceiptTypes", categoryService.findLedgerReceiptTypes());
        modelAndView.addObject("listPaymentMethods", categoryService.findPaymentMethods());
        return baseView(modelAndView);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleSales.readLedgerReceipt(true)")
    public ModelAndView findLedgerReceiptDetail(@PathVariable("id") Integer ledgerReceiptId) {
        Optional<LedgerReceipt> ledgerReceipt = ledgerReceiptService.findById(ledgerReceiptId);
        if (ledgerReceipt.isEmpty()) {
            throw new NotFoundException("Ledger receipt not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SLS_LEDGER_RECEIPT_DETAIL);
        modelAndView.addObject("ledgerReceiptId", ledgerReceiptId);
        modelAndView.addObject("ledgerReceiptDetail", ledgerReceipt.get());
        return baseView(modelAndView);
    }

    @PostMapping("/insert")
    @PreAuthorize("@vldModuleSales.insertLedgerReceipt(true)")
    public ModelAndView insertLedgerReceipt(LedgerReceipt ledgerReceipt) {
        ledgerReceiptService.save(ledgerReceipt);
        return new ModelAndView("redirect:/ledger/receipt");
    }
}