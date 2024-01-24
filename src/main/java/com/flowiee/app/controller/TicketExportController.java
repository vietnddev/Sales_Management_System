package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.entity.TicketExport;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.TicketExportService;
import com.flowiee.app.utils.PagesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("${app.api.prefix}/ticket-export")
public class TicketExportController extends BaseController {
    @Autowired private TicketExportService ticketExportService;

    @GetMapping
    public ModelAndView viewAllTicket() {
        validateModuleStorage.exportGoods(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_TICKET_EXPORT);
        return baseView(modelAndView);
    }
    
    @PostMapping("/create-draft")
    public ApiResponse<TicketExport> createDraftTicket(@RequestBody(required = false) OrderDTO order) {
        try {
            if (!super.validateModuleStorage.exportGoods(true)) {
                return null;
            }
            return ApiResponse.ok(ticketExportService.save(order));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ApiException();
        }
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