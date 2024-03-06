package com.flowiee.sms.controller;

import com.flowiee.sms.core.BaseController;
import com.flowiee.sms.core.vld.ValidateModuleStorage;
import com.flowiee.sms.entity.TicketExport;
import com.flowiee.sms.core.exception.NotFoundException;
import com.flowiee.sms.utils.PagesUtils;
import com.flowiee.sms.service.TicketExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/stg/ticket-export")
public class TicketExportControllerUI extends BaseController {
    @Autowired private TicketExportService ticketExportService;
    @Autowired private ValidateModuleStorage validateModuleStorage;

    @GetMapping
    public ModelAndView viewAllTicketExport() {
        validateModuleStorage.exportGoods(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_TICKET_EXPORT);
        modelAndView.addObject("listTicketExport", ticketExportService.findAll());
        return baseView(modelAndView);
    }

    @GetMapping("/{id}")
    public ModelAndView viewDetail(@PathVariable("id") Integer ticketExportId) {
        validateModuleStorage.exportGoods(true);
        TicketExport ticketExport = ticketExportService.findById(ticketExportId);
        if (ticketExport == null) {
            throw new NotFoundException("Ticket export not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_TICKET_EXPORT_DETAIL);
        modelAndView.addObject("ticketExportId", ticketExportId);
        modelAndView.addObject("ticketExportDetail", ticketExport);
        return baseView(modelAndView);
    }
}