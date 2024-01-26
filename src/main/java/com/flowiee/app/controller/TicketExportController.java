package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.dto.TicketExportDTO;
import com.flowiee.app.entity.TicketExport;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.TicketExportService;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/storage/ticket-export")
@Tag(name = "Ticket export API", description = "Quản lý xuất hàng")
public class TicketExportController extends BaseController {
    @Autowired private TicketExportService ticketExportService;

    @GetMapping
    public ApiResponse<List<TicketExport>> findAll() {
        validateModuleStorage.exportGoods(true);
        return ApiResponse.ok(ticketExportService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<TicketExportDTO> findDetail(@PathVariable("id") Integer ticketExportId) {
        validateModuleStorage.exportGoods(true);
        return ApiResponse.ok(TicketExportDTO.fromTicketExport(ticketExportService.findById(ticketExportId)));
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
    
    @PutMapping("/update/{id}")
    public ApiResponse<TicketExportDTO> updateTicketExport(@RequestBody TicketExport ticketExport, @PathVariable("id") Integer ticketExportId) {
    	validateModuleStorage.exportGoods(true);
        if (ObjectUtils.isEmpty(ticketExportService.findById(ticketExportId))) {
            throw new BadRequestException();
        }
        return ApiResponse.ok(TicketExportDTO.fromTicketExport(ticketExportService.update(ticketExport, ticketExportId)));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteTicketExport(@PathVariable("id") Integer ticketExportId) {
        validateModuleStorage.exportGoods(true);
        TicketExport ticketExportToDelete = ticketExportService.findById(ticketExportId);
        if (ObjectUtils.isEmpty(ticketExportToDelete)) {
            throw new BadRequestException();
        }
        if ("COMPLETED".equals(ticketExportToDelete.getStatus())) {
            throw new BadRequestException(MessageUtils.ERROR_DATA_LOCKED);
        }
        return ApiResponse.ok(ticketExportService.delete(ticketExportId));
    }
}