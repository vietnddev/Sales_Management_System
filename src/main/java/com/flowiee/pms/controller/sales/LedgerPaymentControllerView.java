package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.sales.LedgerPayment;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.service.sales.LedgerPaymentService;
import com.flowiee.pms.utils.PagesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/ledger/payment")
public class LedgerPaymentControllerView extends BaseController {
    @Autowired
    private LedgerPaymentService ledgerPaymentService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @PreAuthorize("@vldModuleSales.readLedgerPayment(true)")
    public ModelAndView getLedgerPayments() {
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SLS_LEDGER_PAYMENT);
        modelAndView.addObject("ledgerPayment", new LedgerPayment());
        modelAndView.addObject("listPaymentGroups", categoryService.findLedgerPaymentGroups());
        modelAndView.addObject("listPaymentTypes", categoryService.findLedgerPaymentTypes());
        modelAndView.addObject("listPaymentMethods", categoryService.findPaymentMethods());
        return modelAndView;
    }

    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleSales.readLedgerPayment(true)")
    public ModelAndView findLedgerPaymentDetail(@PathVariable("id") Integer ledgerPaymentId) {
        Optional<LedgerPayment> ledgerPayment = ledgerPaymentService.findById(ledgerPaymentId);
        if (ledgerPayment.isEmpty()) {
            throw new NotFoundException("Ledger payment not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SLS_LEDGER_PAYMENT_DETAIL);
        modelAndView.addObject("ledgerPaymentId", ledgerPaymentId);
        modelAndView.addObject("ledgerPaymentDetail", ledgerPayment.get());
        return baseView(modelAndView);
    }

    @PostMapping("/insert")
    @PreAuthorize("@vldModuleSales.insertLedgerPayment(true)")
    public ModelAndView insertLedgerPayment(LedgerPayment ledgerPayment) {
        ledgerPaymentService.save(ledgerPayment);
        return new ModelAndView("redirect:/ledger/payment");
    }
}