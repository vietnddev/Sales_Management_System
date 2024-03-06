package com.flowiee.sms.controller;

import com.flowiee.sms.core.BaseController;
import com.flowiee.sms.model.dto.OrderDTO;
import com.flowiee.sms.model.dto.TicketExportDTO;
import com.flowiee.sms.entity.TicketExport;
import com.flowiee.sms.core.exception.AppException;
import com.flowiee.sms.core.exception.BadRequestException;
import com.flowiee.sms.model.ApiResponse;
import com.flowiee.sms.service.TicketExportService;
import com.flowiee.sms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/stg/ticket-export")
@Tag(name = "Ticket export API", description = "Quản lý xuất hàng")
public class TicketExportController extends BaseController {
    @Autowired private TicketExportService ticketExportService;

    @Operation(summary = "Find all tickets")
    @GetMapping("/all")
    public ApiResponse<List<TicketExport>> findAll(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum) {
        vldModuleStorage.exportGoods(true);

        Page<TicketExport> ticketExports = ticketExportService.findAll(pageSize, pageNum - 1);
        return ApiResponse.ok(ticketExports.getContent(), pageNum, pageSize, ticketExports.getTotalPages(), ticketExports.getTotalElements());
    }

    @Operation(summary = "Find detail")
    @GetMapping("/{id}")
    public ApiResponse<TicketExportDTO> findDetail(@PathVariable("id") Integer ticketExportId) {
        vldModuleStorage.exportGoods(true);
        return ApiResponse.ok(TicketExportDTO.fromTicketExport(ticketExportService.findById(ticketExportId)));
    }

    @Operation(summary = "Create new ticket")
    @PostMapping("/create-draft")
    public ApiResponse<TicketExport> createDraftTicket(@RequestBody(required = false) OrderDTO order) {
        try {
            if (!super.vldModuleStorage.exportGoods(true)) {
                return null;
            }
            return ApiResponse.ok(ticketExportService.save(order));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new AppException();
        }
    }

    @Operation(summary = "Update ticket")
    @PutMapping("/update/{id}")
    public ApiResponse<TicketExportDTO> updateTicketExport(@RequestBody TicketExport ticketExport, @PathVariable("id") Integer ticketExportId) {
    	vldModuleStorage.exportGoods(true);
        if (ObjectUtils.isEmpty(ticketExportService.findById(ticketExportId))) {
            throw new BadRequestException();
        }
        return ApiResponse.ok(TicketExportDTO.fromTicketExport(ticketExportService.update(ticketExport, ticketExportId)));
    }

    @Operation(summary = "Delete ticket")
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteTicketExport(@PathVariable("id") Integer ticketExportId) {
        vldModuleStorage.exportGoods(true);
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