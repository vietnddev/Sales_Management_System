package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.TicketExportGoods;
import com.flowiee.app.security.ValidateModuleStorage;
import com.flowiee.app.utils.EndPointUtil;
import com.flowiee.app.utils.PagesUtil;
import com.flowiee.app.service.TicketExportGoodsService;
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
public class TicketExportGoodsController extends BaseController {
    @Autowired
    private TicketExportGoodsService ticketExportGoodsService;
    @Autowired
    private ValidateModuleStorage validateModuleStorage;

    @GetMapping
    public ModelAndView viewAllTicket() {
        validateModuleStorage.exportGoods(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.STG_TICKET_EXPORT);
        return baseView(modelAndView);
    }
    
    @PostMapping("/insert")
    public ModelAndView insertNewTicketExport(@ModelAttribute("ticketExport") TicketExportGoods ticketExport) {
    	validateModuleStorage.exportGoods(true);
    	ticketExportGoodsService.save(ticketExport);
    	return new ModelAndView("redirect:/storage/ticket-export");
    }
    
    @PostMapping("/update/{id}")
    public ModelAndView updateTicketExport(@ModelAttribute("ticketExport") TicketExportGoods ticketExport, @PathVariable("id") Integer ticketExportId) {
    	validateModuleStorage.exportGoods(true);
    	ticketExportGoodsService.update(ticketExport, ticketExportId);
    	return new ModelAndView("redirect:/storage/ticket-export");
    }
    
    @PostMapping("/delete/{id}")
    public ModelAndView deleteTicketExport(@PathVariable("id") Integer ticketExportId) {
    	validateModuleStorage.exportGoods(true);
    	ticketExportGoodsService.delete(ticketExportId);
    	return new ModelAndView("redirect:/storage/ticket-export");
    }
}