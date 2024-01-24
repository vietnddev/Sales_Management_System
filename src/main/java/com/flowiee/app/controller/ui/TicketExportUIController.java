package com.flowiee.app.controller.ui;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.TicketExport;
import com.flowiee.app.security.ValidateModuleStorage;
import com.flowiee.app.utils.EndPointUtil;
import com.flowiee.app.utils.PagesUtils;
import com.flowiee.app.service.TicketExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(EndPointUtil.STORAGE_TICKET_EXPORT)
public class TicketExportUIController extends BaseController {
    @Autowired private TicketExportService   ticketExportService;
    @Autowired private ValidateModuleStorage validateModuleStorage;

    @GetMapping
    public ModelAndView viewAllTicket() {
        validateModuleStorage.exportGoods(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_TICKET_EXPORT);
        modelAndView.addObject("listTicketExport", ticketExportService.findAll());
        return baseView(modelAndView);
    }
    
    @PostMapping("/insert")
    public ModelAndView insertNewTicketExport(@ModelAttribute("ticketExport") TicketExport ticketExport) {
    	validateModuleStorage.exportGoods(true);
    	ticketExportService.save(ticketExport);
    	return new ModelAndView("redirect:/storage/ticket-export");
    }
    
    @PostMapping("/update/{id}")
    public ModelAndView updateTicketExport(@ModelAttribute("ticketExport") TicketExport ticketExport, @PathVariable("id") Integer ticketExportId) {
    	validateModuleStorage.exportGoods(true);
    	ticketExportService.update(ticketExport, ticketExportId);
    	return new ModelAndView("redirect:/storage/ticket-export");
    }
    
    @PostMapping("/delete/{id}")
    public ModelAndView deleteTicketExport(@PathVariable("id") Integer ticketExportId) {
    	validateModuleStorage.exportGoods(true);
    	ticketExportService.delete(ticketExportId);
    	return new ModelAndView("redirect:/storage/ticket-export");
    }
}