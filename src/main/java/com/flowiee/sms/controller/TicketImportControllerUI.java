package com.flowiee.sms.controller;

import com.flowiee.sms.entity.TicketImport;
import com.flowiee.sms.core.exception.NotFoundException;
import com.flowiee.sms.core.vld.ValidateModuleStorage;
import com.flowiee.sms.service.*;
import com.flowiee.sms.utils.*;
import com.flowiee.sms.core.BaseController;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/stg/ticket-import")
public class TicketImportControllerUI extends BaseController {
    @Autowired private TicketImportService ticketImportService;
    @Autowired private ProductService productService;
    @Autowired private MaterialService materialService;
    @Autowired private ValidateModuleStorage validateModuleStorage;

    @GetMapping
    public ModelAndView viewTickets() {
        validateModuleStorage.importGoods(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_TICKET_IMPORT);
        return baseView(modelAndView);
    }

    @GetMapping("/{id}")
    public ModelAndView viewDetail(@PathVariable("id") Integer ticketImportId) {
        validateModuleStorage.importGoods(true);
        TicketImport ticketImport = ticketImportService.findById(ticketImportId);
        if (ObjectUtils.isEmpty(ticketImport)) {
            throw new NotFoundException("Ticket import not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtils.STG_TICKET_IMPORT_DETAIL);
        modelAndView.addObject("ticketImportId", ticketImportId);
        modelAndView.addObject("ticketImportDetail", ticketImport);
        return baseView(modelAndView);
    }

    @ResponseBody
    @GetMapping("/search")
    public void search() {
        validateModuleStorage.importGoods(true);
        List<TicketImport> data = ticketImportService.findAll(null, 1, null, null, null);
        if (data != null) {
            for (TicketImport o : data) {
                System.out.println(o.toString());
            }
        }
    }

//    @GetMapping("/update/{id}")
//    public ModelAndView update(@ModelAttribute("goodsImport") TicketImport ticketImport,
//                         @PathVariable("id") Integer importId,
//                         HttpServletRequest request) {
//        validateModuleStorage.importGoods(true);
//        if (importId <= 0 || ticketImportService.findById(importId) == null) {
//            throw new NotFoundException("Goods import not found!");
//        }
//        ticketImportService.update(ticketImport, importId);
//        return new ModelAndView("redirect:" + request.getHeader("referer"));
//    }

    @GetMapping("/reset/{id}")
    public ModelAndView clear(@PathVariable("id") Integer draftImportId) {
        validateModuleStorage.importGoods(true);
        if (draftImportId <= 0 || ticketImportService.findById(draftImportId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        ticketImportService.delete(draftImportId);
        return new ModelAndView("redirect:");
    }

    @PostMapping("/send-approval/{id}")
    public ModelAndView sendApproval(@PathVariable("id") Integer importId) {
        validateModuleStorage.importGoods(true);
        if (importId <= 0 || ticketImportService.findById(importId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        ticketImportService.updateStatus(importId, "");
        return new ModelAndView("redirect:");
    }

    @PostMapping("/approve/{id}")
    public ModelAndView approve(@PathVariable("id") Integer importId) {
        validateModuleStorage.importGoods(true);
        if (importId <= 0 || ticketImportService.findById(importId) == null) {
            throw new NotFoundException("Goods import not found!");
        }
        ticketImportService.updateStatus(importId, "");
        return new ModelAndView("redirect:");
    }
}