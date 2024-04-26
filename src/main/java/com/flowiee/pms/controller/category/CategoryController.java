package com.flowiee.pms.controller.category;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("${app.api.prefix}/category")
@Tag(name = "Category API", description = "Quản lý danh mục hệ thống")
public class CategoryController extends BaseController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Find all category")
    @GetMapping("/all")
    public AppResponse<List<Category>> findAll() {
        try {
            return success(categoryService.findRootCategory());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "category"), ex);
        }
    }

    @Operation(summary = "Find by type")
    @GetMapping("/{type}")
    public AppResponse<List<Category>> findByType(@PathVariable("type") String categoryType,
                                                  @RequestParam(value = "parentId", required = false) Integer parentId,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                  @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            if (Objects.isNull(pageSize) || Objects.isNull(pageNum)) {
                return success(categoryService.findSubCategory(CommonUtils.getCategoryType(categoryType), parentId));
            }
            Page<Category> categories = categoryService.findSubCategory(CommonUtils.getCategoryType(categoryType), parentId, pageSize, pageNum - 1);
            return success(categories.getContent(), pageNum, pageSize, categories.getTotalPages(), categories.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "category"), ex);
        }
    }

    @Operation(summary = "Create category")
    @PostMapping("/create")
    public AppResponse<Category> createCategory(@RequestBody Category category) {
        try {
            category.setType(CommonUtils.getCategoryType(category.getType()));
            return success(categoryService.save(category));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "category"), ex);
        }
    }

    @Operation(summary = "Update category")
    @PutMapping("/update/{categoryId}")
    public AppResponse<Category> updateCategory(@RequestBody Category category, @PathVariable("categoryId") Integer categoryId) {
        try {
            if (categoryService.findById(categoryId).isEmpty()) {
                throw new BadRequestException();
            }
            category.setType(CommonUtils.getCategoryType(category.getType()));
            if (category.getCode() == null) {
                category.setCode("");
            }
            if (category.getColor() == null) {
                category.setColor("");
            }
            if (category.getNote() == null) {
                category.setNote("");
            }
            return success(categoryService.update(category, categoryId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "category"), ex);
        }
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/delete/{categoryId}")
    public AppResponse<String> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        return success(categoryService.delete(categoryId));
    }
}