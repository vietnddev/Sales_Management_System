package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.sales.LedgerTransaction;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.service.sales.LedgerPaymentService;
import com.flowiee.pms.service.sales.LedgerReceiptService;
import com.flowiee.pms.utils.PagesUtils;
import com.flowiee.pms.utils.constants.LedgerTranType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/ledger/trans/")
public class LedgerTransactionControllerView extends BaseController {
    @Autowired
    private LedgerReceiptService ledgerReceiptService;
    @Autowired
    private LedgerPaymentService ledgerPaymentService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/receipt")
    @PreAuthorize("@vldModuleSales.readLedgerTransaction(true)")
    public ModelAndView getLedgerReceipts() {
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SLS_LEDGER_TRANS);
        modelAndView.addObject("tranTypeKey", LedgerTranType.PT.name());
        modelAndView.addObject("tranTypeName", LedgerTranType.PT.getDescription());
        this.getCategoryOfTransaction(modelAndView, LedgerTranType.PT.name());
        return baseView(modelAndView);
    }

    @GetMapping("/payment")
    @PreAuthorize("@vldModuleSales.readLedgerTransaction(true)")
    public ModelAndView getLedgerPayments() {
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SLS_LEDGER_TRANS);
        modelAndView.addObject("tranTypeKey", LedgerTranType.PC.name());
        modelAndView.addObject("tranTypeName", LedgerTranType.PC.getDescription());
        this.getCategoryOfTransaction(modelAndView, LedgerTranType.PC.name());
        return baseView(modelAndView);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleSales.readLedgerTransaction(true)")
    public ModelAndView findTransactionDetail(@PathVariable("id") Integer tranId) {
        Optional<LedgerTransaction> transaction = ledgerReceiptService.findById(tranId);
        if (transaction.isEmpty()) {
            throw new NotFoundException("Ledger receipt not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SLS_LEDGER_TRANS_DETAIL);
        modelAndView.addObject("tranId", tranId);
        modelAndView.addObject("ledgerTransactionDetail", transaction.get());
        return baseView(modelAndView);
    }

    @PostMapping("/receipt/insert")
    @PreAuthorize("@vldModuleSales.insertLedgerTransaction(true)")
    public ModelAndView insertLedgerReceipt(LedgerTransaction transaction) {
        ledgerReceiptService.save(transaction);
        return new ModelAndView("redirect:/ledger/trans/receipt");
    }

    @PostMapping("/payment/insert")
    @PreAuthorize("@vldModuleSales.insertLedgerTransaction(true)")
    public ModelAndView insertLedgerPayment(LedgerTransaction transaction) {
        ledgerPaymentService.save(transaction);
        return new ModelAndView("redirect:/ledger/trans/payment");
    }

    private void getCategoryOfTransaction(ModelAndView modelAndView, String tranType) {
        modelAndView.addObject("listGroupObjects", categoryService.findLedgerGroupObjects());
        modelAndView.addObject("listPaymentMethods", categoryService.findPaymentMethods());
        if (LedgerTranType.PT.name().equals(tranType)) {
            modelAndView.addObject("listTranContents", categoryService.findLedgerReceiptTypes());
        } else if (LedgerTranType.PC.name().equals(tranType)) {
            modelAndView.addObject("listTranContents", categoryService.findLedgerPaymentTypes());
        }
    }
}