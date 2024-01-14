package com.flowiee.app.controller;

import com.flowiee.app.entity.Category;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.CategoryService;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.ErrorMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/category")
@Tag(name = "Category API", description = "Quản lý danh mục hệ thống")
public class CategoryRestController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Find by type")
    @GetMapping("/{type}")
    public ApiResponse<List<Category>> findByType(@PathVariable("type") String categoryType) {
        try {
            List<Category> result = categoryService.findSubCategory(CommonUtil.getCategoryType(categoryType));
            return ApiResponse.ok(result);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.SEARCH_ERROR_OCCURRED, "category"));
        }
    }
}