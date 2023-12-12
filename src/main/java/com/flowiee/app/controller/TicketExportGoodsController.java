package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.TicketExportGoods;
import com.flowiee.app.security.ValidateModuleStorage;
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
@RequestMapping("/storage/ticket-export")
public class TicketExportGoodsController extends BaseController {
    @Autowired
    private TicketExportGoodsService ticketExportGoodsService;
    @Autowired
    private ValidateModuleStorage validateModuleStorage;

    @GetMapping
    public ModelAndView viewAllTicket() {
        if (!validateModuleStorage.exportGoods(true)) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.STG_TICKET_EXPORT);
        return baseView(modelAndView);
    }
    
    @PostMapping("/insert")
    public String insertNewTicketExport(@ModelAttribute("ticketExport") TicketExportGoods ticketExport) {
    	if (!validateModuleStorage.exportGoods(true)) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
    	ticketExportGoodsService.save(ticketExport);
    	return "redirect:/storage/ticket-export";
    }
    
    @PostMapping("/update/{id}")
    public String updateTicketExport(@ModelAttribute("ticketExport") TicketExportGoods ticketExport, @PathVariable("id") Integer ticketExportId) {
    	if (!validateModuleStorage.exportGoods(true)) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
    	ticketExportGoodsService.update(ticketExport, ticketExportId);
    	return "redirect:/storage/ticket-export";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteTicketExport(@PathVariable("id") Integer ticketExportId) {
    	if (!validateModuleStorage.exportGoods(true)) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
    	ticketExportGoodsService.delete(ticketExportId);
    	return "redirect:/storage/ticket-export";
    }
}