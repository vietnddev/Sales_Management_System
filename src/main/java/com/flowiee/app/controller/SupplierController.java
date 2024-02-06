package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.Supplier;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.SupplierService;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            if (!super.validateModuleStorage.readMaterial(true)) {
                return null;
            }
            if (pageSize != null && pageNum != null) {
                Page<Supplier> suppliers = supplierService.findAll(pageSize, pageNum - 1);
                return ApiResponse.ok(suppliers.getContent(), pageNum, pageSize, suppliers.getTotalPages(), suppliers.getTotalElements());
            } else {
                Page<Supplier> suppliers = supplierService.findAll(null, null);
                return ApiResponse.ok(suppliers.getContent(), 1, 0, suppliers.getTotalPages(), suppliers.getTotalElements());
            }
        } catch (Exception ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "supplier"));
        }
    }
}