package com.flowiee.app.danhmuc.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleDanhMuc;
import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.danhmuc.entity.LoaiKichCo;
import com.flowiee.app.danhmuc.service.LoaiKichCoService;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/danh-muc/loai-kich-co")
public class LoaiKichCoController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoaiKichCoService loaiKichCoService;
    @Autowired
    private KiemTraQuyenModuleDanhMuc kiemTraQuyenModule;

    @GetMapping("")
    public ModelAndView findAll() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_DANHMUC_KICHCO);
            List<LoaiKichCo> list = loaiKichCoService.findAll();
            modelAndView.addObject("listDanhMuc", list);
            modelAndView.addObject("loaiKichCo", new LoaiKichCo());
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
    public String insert(@ModelAttribute("loaiKichCo") LoaiKichCo loaiKichCo) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        loaiKichCoService.save(loaiKichCo);
        return "redirect:";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("loaiKichCo") LoaiKichCo loaiKichCo,
                         @PathVariable("id") int id, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (id <= 0) {
            throw new BadRequestException();
        }
        loaiKichCoService.update(loaiKichCo, id);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        loaiKichCoService.delete(id);
        return "redirect:" + request.getHeader("referer");
    }
}