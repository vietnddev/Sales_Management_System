package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.entity.sales.TicketExport;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.utils.PagesUtils;
import com.flowiee.pms.service.sales.TicketExportService;
import com.flowiee.pms.utils.constants.TicketExportStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/stg/ticket-export")
public class TicketExportControllerView extends BaseController {
    @Autowired
    private TicketExportService ticketExportService;

    @GetMapping
    @PreAuthorize("@vldModuleSales.exportGoods(true)")
    public ModelAndView viewAllTicketExport() {
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_TICKET_EXPORT);
        modelAndView.addObject("listTicketExport", ticketExportService.findAll(-1, -1, null));
        return baseView(modelAndView);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleSales.exportGoods(true)")
    public ModelAndView viewDetail(@PathVariable("id") Integer ticketExportId) {
        Optional<TicketExport> ticketExport = ticketExportService.findById(ticketExportId);
        if (ticketExport.isEmpty()) {
            throw new NotFoundException("Ticket export not found!");
        }
        LinkedHashMap<String, String> ticketExportStatus = new LinkedHashMap<>();
        ticketExportStatus.put(ticketExport.get().getStatus(), TicketExportStatus.valueOf(ticketExport.get().getStatus()).getLabel());
        if (ticketExport.get().getStatus().equals(TicketExportStatus.DRAFT.name())) {
            ticketExportStatus.put(TicketExportStatus.COMPLETED.name(), TicketExportStatus.COMPLETED.getLabel());
            ticketExportStatus.put(TicketExportStatus.CANCEL.name(), TicketExportStatus.CANCEL.getLabel());
        }
        BigDecimal totalValue = BigDecimal.ZERO;
        for (ProductVariantTemp p : ticketExport.get().getListProductVariantTemp()) {
            if (p.getPurchasePrice() != null) {
                totalValue = totalValue.add(p.getPurchasePrice().multiply(new BigDecimal(p.getQuantity())));
            }
        }
        ticketExport.get().setTotalValue(totalValue);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_TICKET_EXPORT_DETAIL);
        modelAndView.addObject("ticketExportId", ticketExportId);
        modelAndView.addObject("ticketExportStatus", ticketExportStatus);
        modelAndView.addObject("ticketExportDetail", ticketExport.get());
        return baseView(modelAndView);
    }
}