package com.flowiee.app.hethong.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModulePhanQuyen;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.hethong.model.Role;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/he-thong/nhom-quyen")
public class NhomQuyenController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private KiemTraQuyenModulePhanQuyen kiemTraQuyenModule;

    @GetMapping
    public ModelAndView showAllRole() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_HETHONG_ROLE);
            modelAndView.addObject("listRole", roleService.findAllRole());
            if (kiemTraQuyenModule.kiemTraQuyenCapNhat()) {
                modelAndView.addObject("action_update", "enable");
            }
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }
}
