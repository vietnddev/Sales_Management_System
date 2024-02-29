package com.flowiee.app.controller.view;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.security.ValidateModuleStorage;
import com.flowiee.app.utils.PagesUtils;
import com.flowiee.app.service.TicketExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/stg/ticket-export")
public class TicketExportUIController extends BaseController {
    @Autowired private TicketExportService   ticketExportService;
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
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_TICKET_EXPORT_DETAIL);
        modelAndView.addObject("ticketExportId", ticketExportId);
        return baseView(modelAndView);
    }
}