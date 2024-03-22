package com.flowiee.pms.controller.sales;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.model.dto.TicketExportDTO;
import com.flowiee.pms.entity.sales.TicketExport;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.ApiResponse;
import com.flowiee.pms.service.sales.TicketExportService;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.api.prefix}/stg/ticket-export")
@Tag(name = "Ticket export API", description = "Quản lý xuất hàng")
public class TicketExportController extends BaseController {
    @Autowired private TicketExportService ticketExportService;

    @Operation(summary = "Find all tickets")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleSales.exportGoods(true)")
    public ApiResponse<List<TicketExport>> findAll(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum) {
        Page<TicketExport> ticketExports = ticketExportService.findAll(pageSize, pageNum - 1);
        return ApiResponse.ok(ticketExports.getContent(), pageNum, pageSize, ticketExports.getTotalPages(), ticketExports.getTotalElements());
    }

    @Operation(summary = "Find detail")
    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleSales.exportGoods(true)")
    public ApiResponse<TicketExportDTO> findDetail(@PathVariable("id") Integer ticketExportId) {
        Optional<TicketExport> ticketExport = ticketExportService.findById(ticketExportId);
        if (ticketExport.isEmpty()) {
            throw new BadRequestException();
        }
        return ApiResponse.ok(TicketExportDTO.fromTicketExport(ticketExport.get()));
    }

    @Operation(summary = "Create new ticket")
    @PostMapping("/create-draft")
    @PreAuthorize("@vldModuleSales.exportGoods(true)")
    public ApiResponse<TicketExport> createDraftTicket(@RequestBody(required = false) OrderDTO order) {
        try {
            return ApiResponse.ok(ticketExportService.save(order));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "ticket export"), ex);
        }
    }

    @Operation(summary = "Update ticket")
    @PutMapping("/update/{id}")
    @PreAuthorize("@vldModuleSales.exportGoods(true)")
    public ApiResponse<TicketExportDTO> updateTicketExport(@RequestBody TicketExport ticketExport, @PathVariable("id") Integer ticketExportId) {
        if (ObjectUtils.isEmpty(ticketExportService.findById(ticketExportId))) {
            throw new BadRequestException();
        }
        return ApiResponse.ok(TicketExportDTO.fromTicketExport(ticketExportService.update(ticketExport, ticketExportId)));
    }

    @Operation(summary = "Delete ticket")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@vldModuleSales.exportGoods(true)")
    public ApiResponse<String> deleteTicketExport(@PathVariable("id") Integer ticketExportId) {
        Optional<TicketExport> ticketExportToDelete = ticketExportService.findById(ticketExportId);
        if (ticketExportToDelete.isEmpty()) {
            throw new BadRequestException();
        }
        if ("COMPLETED".equals(ticketExportToDelete.get().getStatus())) {
            throw new BadRequestException(MessageUtils.ERROR_DATA_LOCKED);
        }
        return ApiResponse.ok(ticketExportService.delete(ticketExportId));
    }
}