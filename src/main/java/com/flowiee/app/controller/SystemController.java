package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.FlowieeConfig;
import com.flowiee.app.entity.Notification;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.security.ValidateModuleSystem;
import com.flowiee.app.service.ConfigService;
import com.flowiee.app.service.RoleService;
import com.flowiee.app.service.SystemLogService;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.PagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/sys")
public class SystemController extends BaseController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ValidateModuleSystem validateModuleSystem;

    @GetMapping("/notification")
    public ModelAndView getAllNotification() {
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_NOTIFICATION);
        modelAndView.addObject("notification", new Notification());
        return baseView(modelAndView);
    }

    @GetMapping(value = "/log")
    public ModelAndView getAllLog() {
        if (!validateModuleSystem.readLog(true)) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_LOG);
        modelAndView.addObject("listLog", systemLogService.getAll());
        return baseView(modelAndView);
    }

    @GetMapping("/config")
    public ModelAndView showConfig() {
        if (!validateModuleSystem.setupConfig(true)) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_CONFIG);
        modelAndView.addObject("config", new FlowieeConfig());
        modelAndView.addObject("listConfig", configService.findAll());
        return baseView(modelAndView);
    }

    @PostMapping("/config/update/{id}")
    public String update(@ModelAttribute("config") FlowieeConfig config,
                         @PathVariable("id") Integer configId) {
        if (!validateModuleSystem.setupConfig(true)) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (configId <= 0 || configService.findById(configId) == null) {
            throw new NotFoundException("Config not found!");
        }
        configService.update(config, configId);
        return "redirect:/he-thong/config";
    }

    @GetMapping("/role")
    public ModelAndView readRole() {
        if (!validateModuleSystem.readPermission(true)) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_ROLE);
        modelAndView.addObject("listRole", roleService.findAllRole());
        modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(CommonUtil.getCurrentAccountId()));
        return baseView(modelAndView);
    }
}