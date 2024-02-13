package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.TicketImportDTO;
import com.flowiee.app.entity.MaterialTemp;
import com.flowiee.app.entity.ProductVariantTemp;
import com.flowiee.app.entity.TicketImport;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.exception.BadRequestException;
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
    public ApiResponse<List<TicketImportDTO>> findAll(@RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum) {
        try {
            if (!super.validateModuleStorage.importGoods(true)) {
                return null;
            }
            Page<TicketImport> ticketImports = ticketImportService.findAll(pageSize, pageNum - 1);
            return ApiResponse.ok(TicketImportDTO.fromTicketImports(ticketImports.getContent()), pageNum, pageSize, ticketImports.getTotalPages(), ticketImports.getTotalElements());
        } catch (Exception ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "ticket import"));
        }
    }

    @Operation(summary = "Find detail phiếu nhập")
    @GetMapping("/{id}")
    public ApiResponse<TicketImportDTO> findDetail(@PathVariable("id") Integer ticketImportId) {
        try {
            if (!super.validateModuleStorage.importGoods(true)) {
                return null;
            }
            return ApiResponse.ok(TicketImportDTO.fromTicketImport(ticketImportService.findById(ticketImportId)));
        } catch (Exception ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "ticket import"));
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
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "ticket import"));
        }
    }

    @Operation(summary = "Cập nhật phiếu nhập hàng")
    @PutMapping("/update/{id}")
    public ApiResponse<TicketImportDTO> updateTicket(@RequestBody TicketImportDTO ticketImportDTO, @PathVariable("id") Integer ticketImportId) {
        if (!super.validateModuleStorage.importGoods(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(TicketImportDTO.fromTicketImport(ticketImportService.update(TicketImport.fromTicketImportDTO(ticketImportDTO), ticketImportId)));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "ticket import"), ex);
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "ticket import"));
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
            logger.error(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "ticket import"), ex);
            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "ticket import"));
        }
    }

    @Operation(summary = "Add sản phẩm vào phiếu nhập hàng")
    @PostMapping("/{id}/add-product")
    public ApiResponse<List<ProductVariantTemp>> addProductVariantToTicket(@PathVariable("id") Integer ticketImportId,
                                                                           @RequestBody List<Integer> productVariantIds) {
        if (!super.validateModuleStorage.importGoods(true)) {
            throw new BadRequestException();
        }
        if (ticketImportId <= 0 || ticketImportService.findById(ticketImportId) == null) {
            throw new BadRequestException("Goods import to add product not found!");
        }
        try {
            return ApiResponse.ok(ticketImportService.addProductToTicket(ticketImportId, productVariantIds));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "ticket_import"), ex);
            throw new AppException();
        }
    }

    @Operation(summary = "Add nguyên vật liệu vào phiếu nhập hàng")
    @PostMapping("/{id}/add-material")
    public ApiResponse<List<MaterialTemp>> addMaterialToTicket(@PathVariable("id") Integer ticketImportId,
                                                               @RequestBody List<Integer> materialIds) {
        if (!super.validateModuleStorage.importGoods(true)) {
            throw new BadRequestException();
        }
        if (ticketImportId <= 0 || ticketImportService.findById(ticketImportId) == null) {
            throw new BadRequestException("Goods import to add product not found!");
        }
        try {
            return ApiResponse.ok(ticketImportService.addMaterialToTicket(ticketImportId, materialIds));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "ticket_import"), ex);
            throw new AppException();
        }
    }
}