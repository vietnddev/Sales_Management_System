package com.flowiee.app.controller.ui;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.Category;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.service.CategoryService;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.utils.PagesUtils;
import com.flowiee.app.security.ValidateModuleCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/system/category")
public class CategoryUIController extends BaseController {
    @Autowired private CategoryService categoryService;
    @Autowired private ValidateModuleCategory validateModuleCategory;

    @GetMapping
    public ModelAndView viewRootCategory() {
        validateModuleCategory.readCategory(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.CTG_CATEGORY);
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("listCategory", categoryService.findRootCategory());
        return baseView(modelAndView);
    }

    @GetMapping("/{type}")
    public ModelAndView viewSubCategory(@PathVariable("type") String categoryType) {
        validateModuleCategory.readCategory(true);
        if (CommonUtils.getCategoryType(categoryType) == null) {
            throw new NotFoundException("Category not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtils.CTG_CATEGORY_DETAIL);
        modelAndView.addObject("categoryType", categoryType);
        modelAndView.addObject("ctgRootName", AppConstants.CATEGORY.valueOf(CommonUtils.getCategoryType(categoryType)).getLabel());
        modelAndView.addObject("templateImportName", AppConstants.TEMPLATE_IE_DM_LOAIDONVITINH);
        modelAndView.addObject("url_template", "");
        modelAndView.addObject("url_import", "");
        modelAndView.addObject("url_export", "");
        return baseView(modelAndView);
    }

    @GetMapping("/{type}/template")
    public ResponseEntity<?> exportTemplate(@PathVariable("type") String categoryType) {
        validateModuleCategory.importCategory(true);
        if (CommonUtils.getCategoryType(categoryType) == null) {
            throw new NotFoundException("Category not found!");
        }
        byte[] dataExport = categoryService.exportTemplate(categoryType);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + AppConstants.TEMPLATE_IE_DM_LOAIDONVITINH + ".xlsx");
        return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
    }

    @PostMapping("/{type}/import")
    public ModelAndView importData(@PathVariable("type") String categoryType, @RequestParam("file") MultipartFile file) {
        validateModuleCategory.importCategory(true);
        if (CommonUtils.getCategoryType(categoryType) == null) {
            throw new NotFoundException("Category not found!");
        }
        categoryService.importData(file, categoryType);
        return new ModelAndView("redirect:/{type}");
    }

    @GetMapping("/{type}/export")
    public ResponseEntity<?> exportData(@PathVariable("type") String categoryType) {
        validateModuleCategory.readCategory(true);
        byte[] dataExport = categoryService.exportData(categoryType);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + AppConstants.TEMPLATE_IE_DM_LOAIDONVITINH + ".xlsx");
        return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
    }
}