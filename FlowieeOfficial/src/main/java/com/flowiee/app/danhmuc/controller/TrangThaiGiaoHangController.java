package com.flowiee.app.danhmuc.controller;

import com.flowiee.app.author.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.danhmuc.entity.TrangThaiGiaoHang;
import com.flowiee.app.danhmuc.service.TrangThaiGiaoHangService;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    private NotificationService notificationService;
    @Autowired
    private KiemTraQuyenModuleDanhMuc kiemTraQuyenModule;

    @GetMapping("")
    public ModelAndView findAll() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_DANHMUC_HINHTHUC_THANHTOAN);
            List<TrangThaiGiaoHang> list = trangThaiGiaoHangService.findAll();
            modelAndView.addObject("listDanhMuc", list);
            modelAndView.addObject("trangThaiGiaoHang", new TrangThaiGiaoHang());
            modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
            if (kiemTraQuyenModule.kiemTraQuyenThemMoi()) {
                modelAndView.addObject("action_create", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenCapNhat()) {
                modelAndView.addObject("action_update", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenXoa()) {
                modelAndView.addObject("action_delete", "enable");
            }
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
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
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        trangThaiGiaoHangService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }
}