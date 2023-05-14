package com.flowiee.app.role.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModulePhanQuyen;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.nguoidung.service.AccountService;
import com.flowiee.app.role.service.AccountRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/role")
public class AccountRoleController {
    @Autowired
    private AccountRoleService roleService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private KiemTraQuyenModulePhanQuyen kiemTraQuyenModule;

    @GetMapping
    public String homeRole(ModelMap modelMap) {
        String username = accountService.getUserName();
        if (username.isEmpty() || username == null) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModule.kiemTraQuyenXem()) {
            //func
            if (kiemTraQuyenModule.kiemTraQuyenThemMoi()) {
                modelMap.addAttribute("action_create", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenCapNhat()) {
                modelMap.addAttribute("action_update", "enable");
            }
            if (kiemTraQuyenModule.kiemTraQuyenXoa()) {
                modelMap.addAttribute("action_delete", "enable");
            }
            return PagesUtil.PAGE_ROLE;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }
}