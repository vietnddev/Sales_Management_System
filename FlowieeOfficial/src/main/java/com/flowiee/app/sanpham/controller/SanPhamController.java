package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleSanPham;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.danhmuc.service.DanhMucService;
import com.flowiee.app.danhmuc.service.LoaiSanPhamService;
import com.flowiee.app.sanpham.entity.ThuocTinhSanPham;
import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.SanPham;
import com.flowiee.app.sanpham.services.*;
import com.flowiee.app.common.utils.PagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/san-pham")
public class SanPhamController {

    @Autowired
    private SanPhamService productsService;
    @Autowired
    private BienTheSanPhamService productVariantService;
    @Autowired
    private ThuocTinhSanPhamService productAttributeService;
    @Autowired
    private DanhMucService categoryService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FileStorageService fileService;
    @Autowired
    private GiaSanPhamService priceHistoryService;
    @Autowired
    private LoaiSanPhamService loaiSanPhamService;
    @Autowired
    private KiemTraQuyenModuleSanPham kiemTraQuyenModule;

    /**
     * Quản lý sản phẩm core
     */
    @GetMapping(value = "")
    public String getAllProducts(ModelMap modelMap) {
        String username = accountService.getUserName();
        if (username == null && username.isEmpty()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            modelMap.addAttribute("sanPham", new SanPham());
            modelMap.addAttribute("listSanPham", productsService.getAllProducts());
            modelMap.addAttribute("listLoaiSanPham", loaiSanPhamService.findAll());
            if (kiemTraQuyenModule.kiemTraQuyenThemMoi()) {
                modelMap.addAttribute("action_create", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenCapNhat()) {
                modelMap.addAttribute("action_update", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenXoa()) {
                modelMap.addAttribute("action_delete", "enable");
            }
            return PagesUtil.PAGE_SANPHAM;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @GetMapping(value = "/{id}") // Show trang tổng quan chi tiết của một sản phẩm
    public String getDetailProduct(ModelMap modelMap, @PathVariable("id") int id) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            modelMap.addAttribute("sanPham", new SanPham());
            modelMap.addAttribute("bienTheSanPham", new BienTheSanPham());
            modelMap.addAttribute("idSanPham", id);
            // Load chi tiết thông tin sản phẩm
            modelMap.addAttribute("detailProducts", productsService.findById(id));
            // Danh sách loại sản phẩm từ danh mục hệ thống
            modelMap.addAttribute("listTypeProducts", loaiSanPhamService.findAll());
            // Danh sách màu sắc từ danh mục hệ thống
            modelMap.addAttribute("listDmMauSacSanPham", categoryService.getListCategory("colorProduct"));
            // Load danh sách biến thể màu sắc
            modelMap.addAttribute("listColorVariant", productVariantService.getListVariantOfProduct("MAU_SAC", id));
            return PagesUtil.PAGE_SANPHAM_TONG_QUAN;
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @PostMapping(value = "/insert") // Thêm mới sản phẩm -> ok
    public String insertProduct(HttpServletRequest request, @ModelAttribute("sanPham") SanPham sanPham) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            productsService.insertProduct(sanPham);
            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @Transactional
    @PostMapping(value = "/update/{id}") //update sản phẩm gốc
    public String updateProduct(HttpServletRequest request, @ModelAttribute("sanPham") SanPham sanPham, @PathVariable("id") int id) {
        String username = accountService.getUserName();
        if (username == null || username.isEmpty()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0 || productsService.findById(id) == null) {
            throw new BadRequestException();
        }
        productsService.update(sanPham, id);
        return "redirect:" + request.getHeader("referer");
    }

    @Transactional
    @PostMapping(value = "/delete/{id}") //delete sản phẩm gốc
    public String deleteProduct(HttpServletRequest request, @PathVariable("id") int id) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            if (productsService.findById(id) != null) {
                productsService.deleteProduct(id);
                System.out.println("Delete successfully");
            } else {
                System.out.println("Product not found!");
            }
            return "redirect:" + request.getHeader("referer");
        }
        return PagesUtil.PAGE_LOGIN;
    }
}