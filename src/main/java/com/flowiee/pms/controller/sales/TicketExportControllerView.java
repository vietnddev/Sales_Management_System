package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.sales.TicketExport;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.utils.PagesUtils;
import com.flowiee.pms.service.sales.TicketExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

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
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_TICKET_EXPORT_DETAIL);
        modelAndView.addObject("ticketExportId", ticketExportId);
        modelAndView.addObject("ticketExportDetail", ticketExport);
        return baseView(modelAndView);
    }
}