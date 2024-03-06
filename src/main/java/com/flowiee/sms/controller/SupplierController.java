package com.flowiee.sms.controller;

import com.flowiee.sms.core.BaseController;
import com.flowiee.sms.entity.Supplier;
import com.flowiee.sms.core.exception.AppException;
import com.flowiee.sms.core.exception.BadRequestException;
import com.flowiee.sms.model.ApiResponse;
import com.flowiee.sms.service.SupplierService;
import com.flowiee.sms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/supplier")
@Tag(name = "Material API", description = "Quản lý nhà cung cấp")
public class SupplierController extends BaseController {
    @Autowired private SupplierService supplierService;

    @Operation(summary = "Find all nhà cung cấp")
    @GetMapping("/all")
    public ApiResponse<List<Supplier>> findAll(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                               @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            if (!super.vldModuleProduct.readSupplier(true)) {
                return null;
            }
            if (pageSize != null && pageNum != null) {
                Page<Supplier> suppliers = supplierService.findAll(pageSize, pageNum - 1);
                return ApiResponse.ok(suppliers.getContent(), pageNum, pageSize, suppliers.getTotalPages(), suppliers.getTotalElements());
            } else {
                Page<Supplier> suppliers = supplierService.findAll(null, null);
                return ApiResponse.ok(suppliers.getContent(), 1, 0, suppliers.getTotalPages(), suppliers.getTotalElements());
            }
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "supplier"), ex);
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "supplier"));
        }
    }

    @Operation(summary = "Thêm mới nhà cung cấp")
    @PostMapping("/create")
    public ApiResponse<Supplier> createNewSupplier(@RequestBody Supplier supplier) {
        try {
            if (!super.vldModuleProduct.insertSupplier(true)) {
                return null;
            }
            return ApiResponse.ok(supplierService.save(supplier));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "supplier"), ex);
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "supplier"));
        }
    }

    @Operation(summary = "Cập nhật nhà cung cấp")
    @PutMapping("/update/{id}")
    public ApiResponse<Supplier> updateSupplier(@RequestBody Supplier supplier, @PathVariable("id") Integer supplierId) {
        try {
            if (!super.vldModuleProduct.updateSupplier(true)) {
                return null;
            }
            if (ObjectUtils.isEmpty(supplierService.findById(supplierId))) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(supplierService.update(supplier, supplierId));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "supplier"), ex);
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "supplier"));
        }
    }

    @Operation(summary = "Xóa nhà cung cấp")
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteSupplier(@PathVariable("id") Integer supplierId) {
        try {
            if (!super.vldModuleProduct.updateSupplier(true)) {
                return null;
            }
            if (ObjectUtils.isEmpty(supplierService.findById(supplierId))) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(supplierService.delete(supplierId));
        } catch (RuntimeException ex) {
            logger.error(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "supplier"), ex);
            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "supplier"));
        }
    }
}