package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.ProductDTO;
import com.flowiee.app.dto.ProductVariantDTO;
import com.flowiee.app.entity.Product;
import com.flowiee.app.entity.ProductVariant;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.utils.ErrorMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/product")
@Tag(name = "Product API", description = "Quản lý sản phẩm")
public class ProductRestController extends BaseController {
    @Autowired
    private ProductService productService;

    @Operation(summary = "Find all products")
    @GetMapping("/all")
    public ApiResponse<List<ProductDTO>> findProducts() {
        if (!super.validateModuleProduct.readProduct(true)) {
            return null;
        }
        try {
            Page<Product> productPage = productService.findAllProducts();
            List<ProductDTO> productList = productService.setInfoVariantOfProduct(ProductDTO.fromProducts(productPage.getContent()));
            return ApiResponse.ok(productList);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.SEARCH_ERROR_OCCURRED, "product"));
        }
    }
    
    @Operation(summary = "Find all variants")
    @GetMapping("/variant/all")
    public ApiResponse<List<ProductVariant>> findProductVariants() {
    	if (!super.validateModuleProduct.readProduct(true)) {
    		return null;
    	}
        try {
            List<ProductVariant> result = productService.findAllProductVariants();
            return ApiResponse.ok(result);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.SEARCH_ERROR_OCCURRED, "product variant"));
        }
    }

    @Operation(summary = "Find all variants of product")
    @GetMapping("/variant/{id}")
    public ApiResponse<List<ProductVariantDTO>> findVariantsOfProduct(@PathVariable("productId") Integer productId) {
        if (!super.validateModuleProduct.readProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.findAllProductVariantOfProduct(productId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.SEARCH_ERROR_OCCURRED, "product variant"));
        }
    }
    
    @Operation(summary = "Create product")
    @PostMapping("/create")
    public ApiResponse<Product> createProduct(@RequestBody Product product) {
        if (!super.validateModuleProduct.insertProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.saveProduct(product));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.CREATE_ERROR_OCCURRED, "product"));
        }
    }
    
    @Operation(summary = "Create product variant")
    @PostMapping("/variant/create")
    public ApiResponse<List<ProductVariant>> createProductVariant(@RequestBody ProductVariant productVariant) {
        if (!super.validateModuleProduct.insertProduct(true)) {
            return null;
        }
        try {
            productService.saveProductVariant(productVariant);
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.CREATE_ERROR_OCCURRED, "product"));
        }
    }
    
    @Operation(summary = "Update product")
    @PutMapping("/update/{id}")
    public ApiResponse<ProductDTO> updateProduct(@RequestBody Product product, @PathVariable("id") Integer productId) {
        if (!super.validateModuleProduct.updateProduct(true)) {
            return null;
        }
        try {
            productService.updateProduct(product, productId);
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.UPDATE_ERROR_OCCURRED, "product"));
        }
    }
    
    @Operation(summary = "Update product variant")
    @PutMapping("/variant/update/{id}")
    public ApiResponse<ProductVariant> updateProductVariant(@RequestBody ProductVariant productVariant, @PathVariable("id") Integer productVariantId) {
        if (!super.validateModuleProduct.updateProduct(true)) {
            return null;
        }
        try {
            productService.updateProductVariant(productVariant, productVariantId);
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.UPDATE_ERROR_OCCURRED, "product"));
        }
    }    
    
    @Operation(summary = "Delete product")
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable("id") Integer productId) {
        if (!super.validateModuleProduct.deleteProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.deleteProduct(productId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.DELETE_ERROR_OCCURRED, "product"));
        }
    }
    
    @Operation(summary = "Delete product variant")
    @DeleteMapping("/variant/delete/{id}")
    public ApiResponse<String> deleteProductVariant(@PathVariable("id") Integer productVariantId) {
        if (!super.validateModuleProduct.deleteProduct(true)) {
            return null;
        }
        try {
            return ApiResponse.ok(productService.deleteProductVariant(productVariantId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.DELETE_ERROR_OCCURRED, "product"));
        }
    }
}