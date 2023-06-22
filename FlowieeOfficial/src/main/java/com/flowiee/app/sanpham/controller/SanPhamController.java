package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleSanPham;
import com.flowiee.app.danhmuc.service.DonViTinhService;
import com.flowiee.app.danhmuc.service.LoaiKichCoService;
import com.flowiee.app.danhmuc.service.LoaiMauSacService;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.danhmuc.service.LoaiSanPhamService;
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
    private BienTheSanPhamService bienTheSanPhamService;
    @Autowired
    private ThuocTinhSanPhamService thuocTinhSanPhamService;
    @Autowired
    private LoaiMauSacService loaiMauSacService;
    @Autowired
    private LoaiKichCoService loaiKichCoService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoaiSanPhamService loaiSanPhamService;
    @Autowired
    private DonViTinhService donViTinhService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private KiemTraQuyenModuleSanPham kiemTraQuyenModule;

    /**
     * Quản lý sản phẩm core
     */
    @GetMapping(value = "")
    public String getAllProducts(ModelMap modelMap) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            modelMap.addAttribute("sanPham", new SanPham());
            modelMap.addAttribute("listSanPham", productsService.findAll());
            modelMap.addAttribute("listLoaiSanPham", loaiSanPhamService.findAll());
            modelMap.addAttribute("listDonViTinh", donViTinhService.findAll());
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

    @GetMapping(value = "/{id}")
    public String getDetailProduct(ModelMap modelMap, @PathVariable("id") int sanPhamId) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        modelMap.addAttribute("sanPham", new SanPham());
        modelMap.addAttribute("bienTheSanPham", new BienTheSanPham());
        modelMap.addAttribute("idSanPham", sanPhamId);
        // Load chi tiết thông tin sản phẩm
        modelMap.addAttribute("detailProducts", productsService.findById(sanPhamId));
        // Danh sách loại sản phẩm từ danh mục hệ thống
        modelMap.addAttribute("listTypeProducts", loaiSanPhamService.findAll());
        // Danh sách màu sắc từ danh mục hệ thống
        modelMap.addAttribute("listDmMauSacSanPham", loaiMauSacService.findAll());
        // Danh sách kích cỡ từ danh mục hệ thống
        modelMap.addAttribute("listDmKichCoSanPham", loaiKichCoService.findAll());
        // Load danh sách biến thể sản phẩm
        modelMap.addAttribute("listColorVariant", bienTheSanPhamService.convertToBienTheSanPhamResponse(bienTheSanPhamService.getListVariantOfProduct(sanPhamId)));
        // Danh sách đơn vị tính từ danh mục hệ thống
        modelMap.addAttribute("listDonViTinh", donViTinhService.findAll());
        //List image
        modelMap.addAttribute("listImageOfSanPham", fileStorageService.getImageOfSanPham(sanPhamId));
        return PagesUtil.PAGE_SANPHAM_TONG_QUAN;
    }

    @PostMapping(value = "/insert")
    public String insertProduct(HttpServletRequest request, @ModelAttribute("sanPham") SanPham sanPham) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        productsService.save(sanPham);
        return "redirect:" + request.getHeader("referer");
    }

    @Transactional
    @PostMapping(value = "/update/{id}")
    public String updateProduct(HttpServletRequest request, @ModelAttribute("sanPham") SanPham sanPham, @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0 || productsService.findById(id) == null) {
            throw new BadRequestException();
        }
        productsService.update(sanPham, id);
        return "redirect:" + request.getHeader("referer");
    }

    @Transactional
    @PostMapping(value = "/delete/{id}")
    public String deleteProduct(HttpServletRequest request, @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (productsService.findById(id) != null) {
            productsService.delete(id);
            System.out.println("Delete successfully");
        } else {
            System.out.println("Product not found!");
        }
        return "redirect:" + request.getHeader("referer");
    }
}