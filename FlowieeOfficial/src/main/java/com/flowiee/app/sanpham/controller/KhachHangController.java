package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleSanPham;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.entity.KhachHang;
import com.flowiee.app.sanpham.model.DonHangRequest;
import com.flowiee.app.sanpham.services.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private KiemTraQuyenModuleSanPham kiemTraQuyenModuleSanPham;

    @GetMapping
    public String findAllDonHang(ModelMap modelMap) {
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
}
