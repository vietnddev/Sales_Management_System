package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.sales.TicketExport;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.utils.constants.Pages;
import com.flowiee.pms.service.sales.TicketExportService;
import com.flowiee.pms.utils.constants.TicketExportStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/stg/ticket-export")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TicketExportControllerView extends BaseController {
    TicketExportService mvTicketExportService;

    @GetMapping
    @PreAuthorize("@vldModuleSales.exportGoods(true)")
    public ModelAndView viewAllTicketExport() {
        ModelAndView modelAndView = new ModelAndView(Pages.STG_TICKET_EXPORT.getTemplate());
        modelAndView.addObject("listTicketExport", mvTicketExportService.findAll(-1, -1, null));
        return baseView(modelAndView);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleSales.exportGoods(true)")
    public ModelAndView viewDetail(@PathVariable("id") Long ticketExportId) {
        Optional<TicketExport> ticketExport = mvTicketExportService.findById(ticketExportId);
        if (ticketExport.isEmpty()) {
            throw new ResourceNotFoundException("Ticket export not found!");
        }
        LinkedHashMap<String, String> ticketExportStatus = new LinkedHashMap<>();
        ticketExportStatus.put(ticketExport.get().getStatus(), TicketExportStatus.valueOf(ticketExport.get().getStatus()).getLabel());
        if (ticketExport.get().getStatus().equals(TicketExportStatus.DRAFT.name())) {
            ticketExportStatus.put(TicketExportStatus.COMPLETED.name(), TicketExportStatus.COMPLETED.getLabel());
            ticketExportStatus.put(TicketExportStatus.CANCEL.name(), TicketExportStatus.CANCEL.getLabel());
        }
        ModelAndView modelAndView = new ModelAndView(Pages.STG_TICKET_EXPORT_DETAIL.getTemplate());
        modelAndView.addObject("ticketExportId", ticketExportId);
        modelAndView.addObject("ticketExportStatus", ticketExportStatus);
        modelAndView.addObject("ticketExportDetail", ticketExport.get());
        return baseView(modelAndView);
    }
}