package com.flowiee.app.danhmuc.controller;

import com.flowiee.app.danhmuc.entity.DanhMuc;
import com.flowiee.app.account.service.AccountService;
import com.flowiee.app.danhmuc.service.DanhMucService;
import com.flowiee.app.common.utils.PagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/danh-muc")
public class DanhMucHeThongController {

    @Autowired
    private DanhMucService categoryService;
    @Autowired
    private AccountService accountService;

    @GetMapping("")
    public String listCategories(ModelMap modelMap) {
        /*
        * Xem danh sách danh mục gốc
        * */
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()){
            modelMap.addAttribute("listCategory", categoryService.getListRootCategory());
            modelMap.addAttribute("category", new DanhMuc());
            return PagesUtil.PAGE_DANHMUC_HETHONG;
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @GetMapping("/{maDanhMuc}")
    public String listSubCategories(ModelMap modelMap, @PathVariable("maDanhMuc") String code) {
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
        modelMap.addAttribute("category", new DanhMuc());
        return PagesUtil.PAGE_DANHMUC_HETHONG;
    }

    @PostMapping("/delete/{id}")
    public String DeleteCategory(ModelMap modelMap, @PathVariable("id") int id, HttpServletRequest request) {
        /*
         * Chức năng: Xóa danh mục
         * Sử dụng tham số referer của header đẻ lưu lại trang vừa truy cập
         * */
        String referer = request.getHeader("referer");
        modelMap.addAttribute("listCategory");
        categoryService.delete(id);
        return "redirect:" + referer;
    }

    @PostMapping("/insert")
    public String insert(ModelMap modelMap, @ModelAttribute("danhMuc") DanhMuc danhMuc, HttpServletRequest request) {
        /*
         * Thêm mới danh mục
         * */
        if (danhMuc.getMaDanhMuc() != null){ //Insert mở màn hình root
            danhMuc.setLoaiDanhMuc("0");
            danhMuc.setMaDanhMuc(danhMuc.getMaDanhMuc());
            categoryService.insertCategory(danhMuc);
        }else { //Insert mở màn hình items
            danhMuc.setMaDanhMuc(danhMuc.getLoaiDanhMuc());
            danhMuc.setLoaiDanhMuc("1");
            categoryService.insertCategory(danhMuc);
        }
        return "redirect:" + request.getHeader("referer");
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/update/{id}")
    public String updateCategory(@ModelAttribute("danhMuc") DanhMuc danhMuc,
                                 @PathVariable("id") int id,
                                 ModelMap modelMap, HttpServletRequest request) {
        /*
         * Cập nhật danh mục
         * */
        categoryService.update(danhMuc, id);
        return "redirect:" + request.getHeader("referer");
    }
}
