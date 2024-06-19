package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.dto.TicketImportDTO;
import com.flowiee.pms.entity.product.MaterialTemp;
import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.entity.sales.TicketImport;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.service.sales.TicketImportService;
import com.flowiee.pms.utils.constants.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.api.prefix}/stg/ticket-import")
@Tag(name = "Ticket import API", description = "Quản lý nhập hàng")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TicketImportController extends BaseController {
    TicketImportService ticketImportService;

    @Operation(summary = "Find all phiếu nhập")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleSales.importGoods(true)")
    public AppResponse<List<TicketImportDTO>> findAll(@RequestParam("pageSize") int pageSize,
                                                      @RequestParam("pageNum") int pageNum,
                                                      @RequestParam(value = "storageId", required = false) Integer storageId) {
        try {
            Page<TicketImport> ticketImports = ticketImportService.findAll(pageSize, pageNum - 1, null, null, null, null, null, storageId);
            return success(TicketImportDTO.fromTicketImports(ticketImports.getContent()), pageNum, pageSize, ticketImports.getTotalPages(), ticketImports.getTotalElements());
        } catch (Exception ex) {
            throw new AppException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "ticket import"), ex);
        }
    }

    @Operation(summary = "Find detail phiếu nhập")
    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleSales.importGoods(true)")
    public AppResponse<TicketImportDTO> findDetail(@PathVariable("id") Integer ticketImportId) {
        Optional<TicketImport> ticketImport = ticketImportService.findById(ticketImportId);
        if (ticketImport.isEmpty()) {
            throw new ResourceNotFoundException("Ticket import goods not found!");
        }
        return success(TicketImportDTO.fromTicketImport(ticketImport.get()));
    }

    @Operation(summary = "Thêm mới phiếu nhập hàng")
    @PostMapping("/create-draft")
    @PreAuthorize("@vldModuleSales.importGoods(true)")
    public AppResponse<TicketImport> createDraftImport(@RequestBody TicketImportDTO ticketImport) {
        try {
            return success(ticketImportService.createDraftTicketImport(ticketImport));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.CREATE_ERROR_OCCURRED.getDescription(), "ticket import"), ex);
        }
    }

    @Operation(summary = "Cập nhật phiếu nhập hàng")
    @PutMapping("/update/{id}")
    @PreAuthorize("@vldModuleSales.importGoods(true)")
    public AppResponse<TicketImportDTO> updateTicket(@RequestBody TicketImportDTO ticketImportDTO, @PathVariable("id") Integer ticketImportId) {
        try {
            return success(TicketImportDTO.fromTicketImport(ticketImportService.update(TicketImport.fromTicketImportDTO(ticketImportDTO), ticketImportId)));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.UPDATE_ERROR_OCCURRED.getDescription(), "ticket import"), ex);
        }
    }

    @Operation(summary = "Xóa phiếu nhập hàng")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@vldModuleSales.importGoods(true)")
    public AppResponse<String> deleteTicket(@PathVariable("id") Integer ticketImportId) {
        return success(ticketImportService.delete(ticketImportId));
    }

    @Operation(summary = "Add sản phẩm vào phiếu nhập hàng")
    @PostMapping("/{id}/add-product")
    @PreAuthorize("@vldModuleSales.importGoods(true)")
    public AppResponse<List<ProductVariantTemp>> addProductVariantToTicket(@PathVariable("id") Integer ticketImportId,
                                                                           @RequestBody List<Integer> productVariantIds) {
        return success(ticketImportService.addProductToTicket(ticketImportId, productVariantIds));
    }

    @Operation(summary = "Add nguyên vật liệu vào phiếu nhập hàng")
    @PostMapping("/{id}/add-material")
    @PreAuthorize("@vldModuleSales.importGoods(true)")
    public AppResponse<List<MaterialTemp>> addMaterialToTicket(@PathVariable("id") Integer ticketImportId,
                                                               @RequestBody List<Integer> materialIds) {
        if (ticketImportId <= 0 || ticketImportService.findById(ticketImportId).isEmpty()) {
            throw new BadRequestException("Goods import to add product not found!");
        }
        try {
            return success(ticketImportService.addMaterialToTicket(ticketImportId, materialIds));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.UPDATE_ERROR_OCCURRED.getDescription(), "ticket_import"), ex);
        }
    }
}