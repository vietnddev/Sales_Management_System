package com.flowiee.pms.controller.sales;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.entity.sales.Supplier;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.ApiResponse;
import com.flowiee.pms.service.sales.SupplierService;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/supplier")
@Tag(name = "Material API", description = "Quản lý nhà cung cấp")
public class SupplierController extends BaseController {
    @Autowired private SupplierService supplierService;

    @Operation(summary = "Find all nhà cung cấp")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleSales.readSupplier(true)")
    public ApiResponse<List<Supplier>> findAll(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                               @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            if (pageSize != null && pageNum != null) {
                Page<Supplier> suppliers = supplierService.findAll(pageSize, pageNum - 1);
                return ApiResponse.ok(suppliers.getContent(), pageNum, pageSize, suppliers.getTotalPages(), suppliers.getTotalElements());
            } else {
                Page<Supplier> suppliers = supplierService.findAll(null, null);
                return ApiResponse.ok(suppliers.getContent(), 1, 0, suppliers.getTotalPages(), suppliers.getTotalElements());
            }
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "supplier"), ex);
        }
    }

    @Operation(summary = "Thêm mới nhà cung cấp")
    @PostMapping("/create")
    @PreAuthorize("@vldModuleSales.insertSupplier(true)")
    public ApiResponse<Supplier> createNewSupplier(@RequestBody Supplier supplier) {
        try {
            return ApiResponse.ok(supplierService.save(supplier));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "supplier"), ex);
        }
    }

    @Operation(summary = "Cập nhật nhà cung cấp")
    @PutMapping("/update/{id}")
    @PreAuthorize("@vldModuleSales.updateSupplier(true)")
    public ApiResponse<Supplier> updateSupplier(@RequestBody Supplier supplier, @PathVariable("id") Integer supplierId) {
        try {
            if (ObjectUtils.isEmpty(supplierService.findById(supplierId))) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(supplierService.update(supplier, supplierId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "supplier"), ex);
        }
    }

    @Operation(summary = "Xóa nhà cung cấp")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@vldModuleSales.deleteSupplier(true)")
    public ApiResponse<String> deleteSupplier(@PathVariable("id") Integer supplierId) {
        return ApiResponse.ok(supplierService.delete(supplierId));
    }
}