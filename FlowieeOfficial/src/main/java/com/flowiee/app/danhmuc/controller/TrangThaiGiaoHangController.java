package com.flowiee.app.danhmuc.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.danhmuc.entity.TrangThaiGiaoHang;
import com.flowiee.app.danhmuc.service.TrangThaiGiaoHangService;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/danh-muc/trang-thai-giao-hang")
public class TrangThaiGiaoHangController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TrangThaiGiaoHangService trangThaiGiaoHangService;
    @Autowired
    private SystemLogService systemLogService;

    @Autowired
    private KiemTraQuyenModuleDanhMuc kiemTraQuyenModule;

    @GetMapping("")
    public String findAll(ModelMap modelMap) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            List<TrangThaiGiaoHang> list = trangThaiGiaoHangService.findAll();
            modelMap.addAttribute("listDanhMuc", list);
            modelMap.addAttribute("trangThaiGiaoHang", new TrangThaiGiaoHang());
            if (kiemTraQuyenModule.kiemTraQuyenThemMoi()) {
                modelMap.addAttribute("action_create", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenCapNhat()) {
                modelMap.addAttribute("action_update", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenXoa()) {
                modelMap.addAttribute("action_delete", "enable");
            }
            return PagesUtil.PAGE_DANHMUC_HINHTHUC_THANHTOAN;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute("trangThaiGiaoHang") TrangThaiGiaoHang trangThaiGiaoHang) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        trangThaiGiaoHangService.save(trangThaiGiaoHang);
        return "redirect:";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("trangThaiGiaoHang") TrangThaiGiaoHang trangThaiGiaoHang,
                         @PathVariable("id") int id, HttpServletRequest request) {
        String username = accountService.getUserName();
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0) {
            throw new BadRequestException();
        }
        trangThaiGiaoHangService.update(trangThaiGiaoHang, id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        String username = accountService.getUserName();
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        trangThaiGiaoHangService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }
}