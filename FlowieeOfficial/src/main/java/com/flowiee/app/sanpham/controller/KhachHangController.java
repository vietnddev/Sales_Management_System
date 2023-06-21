package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleSanPham;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.entity.KhachHang;
import com.flowiee.app.sanpham.model.DonHangRequest;
import com.flowiee.app.sanpham.services.DonHangService;
import com.flowiee.app.sanpham.services.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private DonHangService donHangService;
    @Autowired
    private KiemTraQuyenModuleSanPham kiemTraQuyenModuleSanPham;

    @GetMapping
    public String findAllKhachHang(ModelMap modelMap) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXemKhachHang()) {
            modelMap.addAttribute("listKhachHang", khachHangService.findAll());
            modelMap.addAttribute("khachHang", new KhachHang());
            return PagesUtil.PAGE_KHACHHANG;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @GetMapping("/{id}")
    public String findChiTietKhachHang(ModelMap modelMap, @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXemKhachHang()) {
            modelMap.addAttribute("khachHangDetail", khachHangService.findById(id));
            modelMap.addAttribute("listDonHang", donHangService.findByKhachHangId(id));
            return PagesUtil.PAGE_KHACHHANG_CHITIET;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/create")
    public String createKhachHang(@ModelAttribute("khachHang") KhachHang khachHang) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenThemMoiKhachHang()) {
            khachHangService.save(khachHang);
            return "redirect:/khach-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/update/{id}")
    public String createKhachHang(@ModelAttribute("khachHang") KhachHang khachHang,
                                  @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenCapNhatKhachHang()) {
            khachHangService.update(khachHang, id);
            return "redirect:/khach-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteKhachHang(@PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXoaKhachHang()) {
            khachHangService.delete(id);
            return "redirect:/khach-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }
}