package com.flowiee.pms.controller.category;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.PagesUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@RestController
@RequestMapping("/system/category")
public class CategoryControllerView extends BaseController {
    @Autowired private CategoryService categoryService;

    @GetMapping
    @PreAuthorize("@vldModuleCategory.readCategory(true)")
    public ModelAndView viewRootCategory() {
        ModelAndView modelAndView = new ModelAndView(PagesUtils.CTG_CATEGORY);
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("listCategory", categoryService.findRootCategory());
        return baseView(modelAndView);
    }

    @GetMapping("/{type}")
    @PreAuthorize("@vldModuleCategory.readCategory(true)")
    public ModelAndView viewSubCategory(@PathVariable("type") String categoryType) {
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
    @PreAuthorize("@vldModuleCategory.importCategory(true)")
    public ResponseEntity<?> exportTemplate(@PathVariable("type") String categoryType) {
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
    @PreAuthorize("@vldModuleCategory.importCategory(true)")
    public ModelAndView importData(@PathVariable("type") String categoryType, @RequestParam("file") MultipartFile file) {
        if (CommonUtils.getCategoryType(categoryType) == null) {
            throw new NotFoundException("Category not found!");
        }
        categoryService.importData(file, categoryType);
        return new ModelAndView("redirect:/{type}");
    }

    @GetMapping("/{type}/export")
    @PreAuthorize("@vldModuleCategory.readCategory(true)")
    public ResponseEntity<?> exportData(@PathVariable("type") String categoryType) {
        byte[] dataExport = categoryService.exportData(categoryType);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + AppConstants.TEMPLATE_IE_DM_LOAIDONVITINH + ".xlsx");
        return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
    }
}