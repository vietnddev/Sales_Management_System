package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.TicketImport;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.TicketImportService;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/storage/ticket-import")
@Tag(name = "Ticket import API", description = "Quản lý nhập hàng")
public class TicketImportController extends BaseController {
    @Autowired private TicketImportService ticketImportService;

    @Operation(summary = "Find all phiếu nhập")
    @GetMapping("/all")
    public ApiResponse<List<TicketImport>> findAll(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum) {
        try {
            if (!super.validateModuleStorage.importGoods(true)) {
                return null;
            }
            Page<TicketImport> logPage = ticketImportService.findAll(pageSize, pageNum - 1);
            return ApiResponse.ok(logPage.getContent(), pageNum, pageSize, logPage.getTotalPages(), logPage.getTotalElements());
        } catch (Exception ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "ticket import"));
        }
    }

    @Operation(summary = "Find all phiếu nhập")
    @GetMapping("/{id}")
    public ApiResponse<TicketImport> findDetail(@PathVariable("id") Integer ticketImportId) {
        try {
            if (!super.validateModuleStorage.importGoods(true)) {
                return null;
            }
            return ApiResponse.ok(ticketImportService.findById(ticketImportId));
        } catch (Exception ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "ticket import"));
        }
    }

    @Operation(summary = "Thêm mới phiếu nhập hàng")
    @PostMapping("/create-draft")
    public ApiResponse<TicketImport> createDraftImport(@RequestBody TicketImport ticketImport) {
        if (!super.validateModuleStorage.importGoods(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(ticketImportService.createDraftTicketImport(ticketImport.getTitle()));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ApiException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "ticket import"));
        }
    }

    @Operation(summary = "Cập nhật phiếu nhập hàng")
    @PutMapping("/update/{id}")
    public ApiResponse<TicketImport> updateTicket(@RequestBody TicketImport ticketImport, @PathVariable("id") Integer ticketImportId) {
        if (!super.validateModuleStorage.importGoods(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(ticketImportService.update(ticketImport, ticketImportId));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ApiException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "ticket import"));
        }
    }

    @Operation(summary = "Xóa phiếu nhập hàng")
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteTicket(@PathVariable("id") Integer ticketImportId) {
        if (!super.validateModuleStorage.importGoods(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(ticketImportService.delete(ticketImportId));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new ApiException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "ticket import"));
        }
    }
}