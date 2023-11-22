package com.flowiee.app.controller;

import com.flowiee.app.config.author.ValidateModuleSystem;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.NotificationService;
import com.flowiee.app.service.RoleService;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.utils.PagesUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/he-thong/nhom-quyen")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ValidateModuleSystem validateModuleSystem;

    @GetMapping
    public ModelAndView readRole() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleSystem.readPermission()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_ROLE);
        modelAndView.addObject("listRole", roleService.findAllRole());
        modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(accountService.findCurrentAccountId()));
        if (validateModuleSystem.updatePermission()) {
            modelAndView.addObject("action_update", "enable");
        }
        return baseView(modelAndView);
    }
}