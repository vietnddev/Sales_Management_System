package com.flowiee.app.controller.rest;

import com.flowiee.app.entity.Category;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.CategoryService;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.utils.MessageUtils;
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

    @Operation(summary = "Find all category")
    @GetMapping("/all")
    public ApiResponse<List<Category>> findAll() {
        try {
            return ApiResponse.ok(categoryService.findRootCategory());
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "category"));
        }
    }

    @Operation(summary = "Find by type")
    @GetMapping("/{type}")
    public ApiResponse<List<Category>> findByType(@PathVariable("type") String categoryType,
                                                  @RequestParam(value = "parentId", required = false) Integer parentId) {
        try {
            List<Category> result = categoryService.findSubCategory(CommonUtils.getCategoryType(categoryType), parentId);
            return ApiResponse.ok(result);
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "category"));
        }
    }

    @Operation(summary = "Create category")
    @PostMapping("/create")
    public ApiResponse<Category> createCategory(@RequestBody Category category) {
        try {
            return ApiResponse.ok(categoryService.save(category));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "category"));
        }
    }

    @Operation(summary = "Update category")
    @PutMapping("/update/{categoryId}")
    public ApiResponse<Category> updateCategory(@RequestBody Category category, @PathVariable("categoryId") Integer categoryId) {
        try {
            if (categoryService.findById(categoryId) == null) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(categoryService.update(category, categoryId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "category"));
        }
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/delete/{categoryId}")
    public ApiResponse<String> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        try {
            if (categoryService.findById(categoryId) == null) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(categoryService.delete(categoryId));
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "category"));
        }
    }
}