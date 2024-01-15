package com.flowiee.app.controller;

import com.flowiee.app.entity.Category;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.CategoryService;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.ErrorMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Create category")
    @PostMapping("/create")
    public ApiResponse<Category> createCategory(@RequestBody Category category) {
        try {
            return ApiResponse.ok(null);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.CREATE_ERROR_OCCURRED, "category"));
        }
    }

    @Operation(summary = "Update category")
    @PutMapping("/update/{id}")
    public ApiResponse<Category> updateCategory(@RequestBody Category category, @PathVariable("id") Integer categoryId) {
        try {
            if (categoryService.findById(categoryId) == null) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(categoryService.update(category, categoryId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.UPDATE_ERROR_OCCURRED, "category"));
        }
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable("id") Integer categoryId) {
        try {
            if (categoryService.findById(categoryId) == null) {
                throw new BadRequestException();
            }
            categoryService.delete(categoryId);
            return ApiResponse.ok("Category deleted successfully!");
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(ErrorMessages.DELETE_ERROR_OCCURRED, "category"));
        }
    }
}