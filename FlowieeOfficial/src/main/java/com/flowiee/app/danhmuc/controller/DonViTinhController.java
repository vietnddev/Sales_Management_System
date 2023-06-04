package com.flowiee.app.danhmuc.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.danhmuc.entity.DonViTinh;
import com.flowiee.app.danhmuc.entity.KenhBanHang;
import com.flowiee.app.danhmuc.service.DonViTinhService;
import com.flowiee.app.danhmuc.service.KenhBanHangService;
import com.flowiee.app.hethong.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/danh-muc/don-vi-tinh")
public class DonViTinhController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private DonViTinhService donViTinhService;
    @Autowired
    private KiemTraQuyenModuleDanhMuc kiemTraQuyenModule;

    @GetMapping("")
    public String findAll(ModelMap modelMap) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            List<DonViTinh> list = donViTinhService.findAll();
            modelMap.addAttribute("listDanhMuc", list);
            modelMap.addAttribute("donViTinh", new DonViTinh());
            if (kiemTraQuyenModule.kiemTraQuyenThemMoi()) {
                modelMap.addAttribute("action_create", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenCapNhat()) {
                modelMap.addAttribute("action_update", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenXoa()) {
                modelMap.addAttribute("action_delete", "enable");
            }
            return PagesUtil.PAGE_DANHMUC_DONVITINH;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("donViTinh") DonViTinh donViTinh) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        donViTinhService.save(donViTinh);
        return "redirect:";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("donViTinh") DonViTinh donViTinh,
                         @PathVariable("id") int id, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0) {
            throw new BadRequestException();
        }
        donViTinhService.update(donViTinh, id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        donViTinhService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }
}