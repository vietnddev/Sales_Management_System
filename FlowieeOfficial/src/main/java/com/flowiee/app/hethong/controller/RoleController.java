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
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private KiemTraQuyenModulePhanQuyen kiemTraQuyenModule;

    @GetMapping
    public String showAllRole(ModelMap modelMap) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            modelMap.addAttribute("listRole", roleService.findAllRole());
            if (kiemTraQuyenModule.kiemTraQuyenCapNhat()) {
                modelMap.addAttribute("action_update", "enable");
            }
            return PagesUtil.PAGE_ROLE;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }
}