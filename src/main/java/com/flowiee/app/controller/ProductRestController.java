package com.flowiee.app.controller;

import com.flowiee.app.entity.ProductVariant;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.utils.ErrorMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/product")
@Tag(name = "Product API", description = "Quản lý sản phẩm")
public class ProductRestController {
    @Autowired
    private ProductService productService;

    @Operation(summary = "Find all variants")
    @GetMapping("/variant/all")
    public ApiResponse<List<ProductVariant>> findProductVariants() {
        try {
            List<ProductVariant> result = productService.findAllProductVariants();
            return ApiResponse.ok(result);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.SEARCH_ERROR_OCCURRED, "product variant"));
        }
    }
}