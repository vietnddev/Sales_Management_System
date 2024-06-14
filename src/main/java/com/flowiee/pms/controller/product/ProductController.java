package com.flowiee.pms.controller.product;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.product.Product;
import com.flowiee.pms.entity.product.ProductHistory;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.dto.ProductDTO;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.service.ExportService;
import com.flowiee.pms.service.product.*;
import com.flowiee.pms.utils.MessageUtils;
import com.flowiee.pms.utils.converter.ProductConvert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.api.prefix}/product")
@Tag(name = "Product API", description = "Quản lý sản phẩm")
public class ProductController extends BaseController {
    private final ProductInfoService    productInfoService;
    private final ProductHistoryService productHistoryService;
    private final ExportService         exportService;

    public ProductController(ProductInfoService productInfoService, ProductHistoryService productHistoryService,
                             @Qualifier("productExportServiceImpl") ExportService exportService) {
        this.productInfoService = productInfoService;
        this.productHistoryService = productHistoryService;
        this.exportService = exportService;
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
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product"), ex);
        }
    }

    @Operation(summary = "Find detail products")
    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleProduct.readProduct(true)")
    public AppResponse<ProductDTO> findDetailProduct(@PathVariable("id") Integer productId) {
        try {
            Optional<ProductDTO> product = productInfoService.findById(productId);
            if (product.isEmpty()) {
                throw new BadRequestException();
            }
            return success(product.get());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product"), ex);
        }
    }

    @Operation(summary = "Create product")
    @PostMapping("/create")
    @PreAuthorize("@vldModuleProduct.insertProduct(true)")
    public AppResponse<Product> createProduct(@RequestBody ProductDTO product) {
        try {
            return success(productInfoService.save(product));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "product"), ex);
        }
    }

    @Operation(summary = "Update product")
    @PutMapping("/update/{id}")
    @PreAuthorize("@vldModuleProduct.updateProduct(true)")
    public AppResponse<ProductDTO> updateProduct(@RequestBody ProductDTO product, @PathVariable("id") Integer productId) {
        try {
            return success(ProductConvert.convertToDTO(productInfoService.update(product, productId)));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "product"), ex);
        }
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
        try {
            if (ObjectUtils.isEmpty(productInfoService.findById(productId))) {
                throw new BadRequestException();
            }
            return success(productHistoryService.findByProduct(productId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "product history"), ex);
        }
    }
}