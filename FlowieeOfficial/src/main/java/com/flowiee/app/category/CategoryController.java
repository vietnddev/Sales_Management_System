package com.flowiee.app.category;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.utils.CategoryUtil;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.config.author.ValidateModuleCategory;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.service.system.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/system/category")
public class CategoryController extends BaseController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ValidateModuleCategory validateModuleCategory;

    @GetMapping("")
    public ModelAndView viewRootCategory() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (validateModuleCategory.readCategory()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.CTG_CATEGORY);
            modelAndView.addObject("category", new Category());
            modelAndView.addObject("listCategory", categoryService.findRootCategory());            
            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
    }

    @GetMapping("/{type}")
    public ModelAndView viewSubCategory(@PathVariable("type") String categoryType) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleCategory.readCategory()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        if (CategoryUtil.getCategoryType(categoryType) == null) {
            throw new NotFoundException("Category not found!");
        }
        List<Category> listCategory = categoryService.findSubCategory(CategoryUtil.getCategoryType(categoryType));
        ModelAndView modelAndView = new ModelAndView(PagesUtil.CTG_CATEGORY);
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("listCategory", listCategory);
        modelAndView.addObject("categoryType", categoryType);
        modelAndView.addObject("templateImportName", FileUtil.TEMPLATE_IE_DM_LOAIDONVITINH);
        modelAndView.addObject("url_template", "");
        modelAndView.addObject("url_import", "");
        modelAndView.addObject("url_export", "");
        return baseView(modelAndView);
    }

    @PostMapping("/{type}/insert")
    public String insert(@ModelAttribute("category") Category category, 
    					 @PathVariable("type") String categoryType,
    					 HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleCategory.insertCategory()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (CategoryUtil.getCategoryType(categoryType) == null) {
            throw new NotFoundException("Category not found!");
        }
        category.setType(CategoryUtil.getCategoryType(categoryType));
        categoryService.save(category);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("category") Category category,
                         @PathVariable("id") Integer categoryId, 
                         HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleCategory.updateCategory()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (category.getType() == null || categoryId <= 0 || categoryService.findById(categoryId) == null) {
            throw new NotFoundException("Category not found!");
        }
        categoryService.update(category, categoryId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer categoryId, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (categoryId <= 0 || categoryService.findById(categoryId) == null) {
            throw new NotFoundException("Category not found!");
        }
        categoryService.delete(categoryId);
        return "redirect:" + request.getHeader("referer");
    }
    
    @GetMapping("/{type}/template")
    public ResponseEntity<?> exportTemplate(@PathVariable("type") String categoryType) {
        if (!accountService.isLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleCategory.importCategory()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.SYS_UNAUTHORIZED);
        }
        if (CategoryUtil.getCategoryType(categoryType) == null) {
            throw new NotFoundException("Category not found!");
        }
        byte[] dataExport = categoryService.exportTemplate(categoryType);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FileUtil.TEMPLATE_IE_DM_LOAIDONVITINH + ".xlsx");
        return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
    }

    @PostMapping("/{type}/import")
    public String importData(@PathVariable("type") String categoryType, @RequestParam("file")MultipartFile file) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleCategory.importCategory()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (CategoryUtil.getCategoryType(categoryType) == null) {
            throw new NotFoundException("Category not found!");
        }
        categoryService.importData(file, categoryType);
        return "redirect:/{type}";
    }

    @GetMapping("/{type}/export")
    public ResponseEntity<?> exportData(@PathVariable("type") String categoryType) {
        if (!accountService.isLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleCategory.readCategory()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.SYS_UNAUTHORIZED);
        }
        byte[] dataExport = categoryService.exportData(categoryType);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FileUtil.TEMPLATE_IE_DM_LOAIDONVITINH + ".xlsx");
        return new ResponseEntity<>(new ByteArrayResource(dataExport), header, HttpStatus.CREATED);
    }
}