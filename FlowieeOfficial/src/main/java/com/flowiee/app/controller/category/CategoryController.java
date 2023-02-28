package com.flowiee.app.controller.category;

import com.flowiee.app.model.category.Category;
import com.flowiee.app.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String listCategories(ModelMap modelMap) {
        /*
        * Xem danh sách danh mục gốc
        * */
        modelMap.addAttribute("listCategory", categoryService.getListRootCategory());
        modelMap.addAttribute("category", new Category());
        return "pages/category/category";
    }

    @GetMapping("/{code}")
    public String listSubCategories(ModelMap modelMap, @PathVariable("code") String code) {
        /*
        * Xem chi tiết list item của loại danh mục, truy vấn theo code được truyền vào
        * */
        modelMap.addAttribute("listCategory", categoryService.getListCategory(code));
        // Dán list root name để insert trong màn items
        modelMap.addAttribute("listRootName", categoryService.getListRootCategory());
        // Dán tên của loại danh mục lên popup update
        modelMap.addAttribute("nameItem", categoryService.getNameItem(code, "0"));
        // Dán code của danh mục gốc cho option select của form insert
        modelMap.addAttribute("codeItem", code);
        modelMap.addAttribute("category", new Category());
        return "pages/category/category";
    }

    @PostMapping("/delete-{id}")
    public String DeleteCategory(ModelMap modelMap, @PathVariable("id") int id, HttpServletRequest request) {
        /*
         * Chức năng: Xóa danh mục
         * Sử dụng tham số referer của header đẻ lưu lại trang vừa truy cập
         * */
        String referer = request.getHeader("referer");
        modelMap.addAttribute("listCategory");
        categoryService.deleteCategory(id);
        return "redirect:" + referer;
    }

    @PostMapping("/insert")
    public String InsertCategory(ModelMap modelMap, @ModelAttribute("category") Category category, HttpServletRequest request) {
        /*
         * Thêm mới danh mục
         * */
        if (category.getCode() != null){ //Insert mở màn hình root
            category.setType("0");
            category.setCode(category.getCode());
            categoryService.insertCategory(category);
        }else { //Insert mở màn hình items
            category.setCode(category.getType());
            category.setType("1");
            categoryService.insertCategory(category);
        }
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/update")
    public String UpdateCategory(ModelMap modelMap, @ModelAttribute("category") Category category, HttpServletRequest request) {
        /*
         * Thêm mới danh mục
         * */
        categoryService.updateCategory(category);
        return "redirect:" + request.getHeader("referer");
    }
}
