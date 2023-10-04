package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.sanpham.entity.KhachHang;
import com.flowiee.app.sanpham.services.DonHangService;
import com.flowiee.app.sanpham.services.KhachHangService;
import com.flowiee.app.security.authorization.KiemTraQuyenModuleSanPham;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
    public ModelAndView findAllKhachHang() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXemKhachHang()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_KHACHHANG);
            modelAndView.addObject("listKhachHang", khachHangService.findAll());
            modelAndView.addObject("khachHang", new KhachHang());
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ModelAndView findChiTietKhachHang(@PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXemKhachHang()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_KHACHHANG_CHITIET);
            modelAndView.addObject("khachHangDetail", khachHangService.findById(id));
            modelAndView.addObject("listDonHang", donHangService.findByKhachHangId(id));
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @PostMapping("/create")
    public String createKhachHang(@ModelAttribute("khachHang") KhachHang khachHang,
                                  HttpServletRequest request) {
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