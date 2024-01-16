package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.Category;
import com.flowiee.app.entity.FlowieeConfig;
import com.flowiee.app.entity.Notification;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.security.ValidateModuleSystem;
import com.flowiee.app.service.*;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.utils.EndPointUtil;
import com.flowiee.app.utils.PagesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
public class SystemController extends BaseController {
    @Autowired private ConfigService configService;
    @Autowired private SystemLogService systemLogService;
    @Autowired private RoleService roleService;
    @Autowired private CategoryService categoryService;
    @Autowired private ValidateModuleSystem validateModuleSystem;
    @Autowired private NotificationService notificationService;

    @GetMapping(EndPointUtil.SYS_NOTIFICATION)
    public ModelAndView getAllNotification() {
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_NOTIFICATION);
        modelAndView.addObject("notification", new Notification());
        return baseView(modelAndView);
    }

    @GetMapping(EndPointUtil.SYS_LOG)
    public ModelAndView getAllLog() {
        validateModuleSystem.readLog(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_LOG);
        modelAndView.addObject("listLog", systemLogService.getAll());
        return baseView(modelAndView);
    }

    @GetMapping(EndPointUtil.SYS_CONFIG)
    public ModelAndView showConfig() {
        validateModuleSystem.setupConfig(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_CONFIG);
        modelAndView.addObject("config", new FlowieeConfig());
        modelAndView.addObject("listConfig", configService.findAll());
        return baseView(modelAndView);
    }

    @PostMapping("/sys/config/update/{id}")
    public ModelAndView update(@ModelAttribute("config") FlowieeConfig config,
                         @PathVariable("id") Integer configId) {
        validateModuleSystem.setupConfig(true);
        if (configId <= 0 || configService.findById(configId) == null) {
            throw new NotFoundException("Config not found!");
        }
        configService.update(config, configId);
        return new ModelAndView("redirect:/he-thong/config");
    }

    @GetMapping(EndPointUtil.SYS_ROLE)
    public ModelAndView readRole() {
        validateModuleSystem.readPermission(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_ROLE);
        modelAndView.addObject("listRole", roleService.findAllRole());
        modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(CommonUtils.getCurrentAccountId()));
        return baseView(modelAndView);
    }

    @PostMapping("/sys/refresh")
    public ModelAndView refreshApp() {
        List<Category> rootCategories = categoryService.findRootCategory();
        for (Category c : rootCategories) {
            if (c.getType() != null && !c.getType().trim().isEmpty()) {
                AppConstants.CATEGORY.valueOf(c.getType()).setLabel(c.getName());
            }
        }
        return new ModelAndView("redirect:/sys/config");
    }
}