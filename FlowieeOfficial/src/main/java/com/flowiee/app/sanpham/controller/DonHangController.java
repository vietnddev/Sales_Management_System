package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleSanPham;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.model.DonHangRequest;
import com.flowiee.app.sanpham.services.BienTheSanPhamService;
import com.flowiee.app.sanpham.services.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/don-hang")
public class DonHangController {
    @Autowired
    private DonHangService donHangService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;
    @Autowired
    private KiemTraQuyenModuleSanPham kiemTraQuyenModuleSanPham;

    @GetMapping
    public String getAllDonHang(ModelMap modelMap) {
        String username = accountService.getUserName();
        if (username == null || username.isEmpty()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXem()) {
            modelMap.addAttribute("listDonHang", donHangService.findAll());
            modelMap.addAttribute("listBienTheSanPham", bienTheSanPhamService.findAll());
            modelMap.addAttribute("donHangRequest", new DonHangRequest());
            modelMap.addAttribute("donHang", new DonHang());
            return PagesUtil.PAGE_DONHANG;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("donHangRequest") DonHangRequest request) {
        String username = accountService.getUserName();
        if (username == null || username.isEmpty()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenThemMoiDonHang()) {
            donHangService.save(request);
            return "redirect:/don-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("donHang") DonHang donHang, @PathVariable("id") int id) {
        String username = accountService.getUserName();
        if (username == null || username.isEmpty()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenCapNhatDonHang()) {
            donHangService.update(donHang, id);
            return "redirect:/don-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        String username = accountService.getUserName();
        if (username == null || username.isEmpty()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenCapNhatDonHang()) {
            donHangService.delete(id);
            return "redirect:/don-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }
}