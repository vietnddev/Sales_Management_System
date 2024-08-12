package com.flowiee.pms.controller.product;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.product.Product;
import com.flowiee.pms.entity.product.ProductHistory;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.EximModel;
import com.flowiee.pms.model.dto.ProductDTO;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.service.ExportService;
import com.flowiee.pms.service.ImportService;
import com.flowiee.pms.service.product.*;
import com.flowiee.pms.utils.constants.ErrorCode;
import com.flowiee.pms.utils.constants.TemplateExport;
import com.flowiee.pms.utils.converter.ProductConvert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.api.prefix}/product")
@Tag(name = "Product API", description = "Quản lý sản phẩm")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController extends BaseController {
    ProductInfoService    productInfoService;
    ProductHistoryService productHistoryService;
    ExportService         exportService;
    ImportService         importService;

    public ProductController(ProductInfoService productInfoService, ProductHistoryService productHistoryService,
                             @Qualifier("productExportServiceImpl") ExportService exportService, @Qualifier("productImportServiceImpl") ImportService importService) {
        this.productInfoService = productInfoService;
        this.productHistoryService = productHistoryService;
        this.exportService = exportService;
        this.importService = importService;
    }

    @Operation(summary = "Find all products")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleProduct.readProduct(true)")
    public AppResponse<List<ProductDTO>> findProducts(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                      @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                      @RequestParam(value = "txtSearch", required = false) String txtSearch,
                                                      @RequestParam(value = "brandId", required = false) Integer pBrand,
                                                      @RequestParam(value = "productTypeId", required = false) Integer pProductType,
                                                      @RequestParam(value = "colorId", required = false) Integer pColor,
                                                      @RequestParam(value = "sizeId", required = false) Integer pSize,
                                                      @RequestParam(value = "unitId", required = false) Integer pUnit,
                                                      @RequestParam(value = "fullInfo", required = false) Boolean fullInfo) {
        try {
            if (fullInfo != null && !fullInfo) {
                return success(ProductConvert.convertToDTOs(productInfoService.findProductsIdAndProductName()));
            }
            Page<ProductDTO> productPage = productInfoService.findAll(pageSize, pageNum - 1, txtSearch, pBrand, pProductType, pColor, pSize, pUnit, null);
            return success(productPage.getContent(), pageNum, pageSize, productPage.getTotalPages(), productPage.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "product"), ex);
        }
    }

    @Operation(summary = "Find detail products")
    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleProduct.readProduct(true)")
    public AppResponse<ProductDTO> findDetailProduct(@PathVariable("id") Integer productId) {
        Optional<ProductDTO> product = productInfoService.findById(productId);
        if (product.isEmpty()) {
            throw new ResourceNotFoundException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "product"));
        }
        return success(product.get());
    }

    @Operation(summary = "Create product")
    @PostMapping("/create")
    @PreAuthorize("@vldModuleProduct.insertProduct(true)")
    public AppResponse<Product> createProduct(@RequestBody ProductDTO product) {
        try {
            return success(productInfoService.save(product));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.CREATE_ERROR_OCCURRED.getDescription(), "product"), ex);
        }
    }

    @Operation(summary = "Update product")
    @PutMapping("/update/{id}")
    @PreAuthorize("@vldModuleProduct.updateProduct(true)")
    public AppResponse<ProductDTO> updateProduct(@RequestBody ProductDTO product, @PathVariable("id") Integer productId) {
        if (productInfoService.findById(productId).isEmpty()) {
            throw new AppException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "product"));
        }
        return success(ProductConvert.convertToDTO(productInfoService.update(product, productId)));
    }

    @Operation(summary = "Delete product")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@vldModuleProduct.deleteProduct(true)")
    public AppResponse<String> deleteProduct(@PathVariable("id") Integer productId) {
        return success(productInfoService.delete(productId));
    }

    @Operation(summary = "Get histories of product")
    @GetMapping(value = "/{productId}/history")
    @PreAuthorize("@vldModuleProduct.readProduct(true)")
    public AppResponse<List<ProductHistory>> getHistoryOfProduct(@PathVariable("productId") Integer productId) {
        if (ObjectUtils.isEmpty(productInfoService.findById(productId))) {
            throw new ResourceNotFoundException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "product history"));
        }
        return success(productHistoryService.findByProduct(productId));
    }

    @GetMapping("/import")
    @PreAuthorize("@vldModuleProduct.insertProduct(true)")
    public void importData(@RequestParam("file") MultipartFile file) {
        importService.importFromExcel(TemplateExport.IM_LIST_OF_PRODUCTS, file);
    }
}