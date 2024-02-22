package com.flowiee.app.controller.ui;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.Category;
import com.flowiee.app.entity.SystemConfig;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.service.*;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.PagesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping
public class SystemUIController extends BaseController {
    @Autowired private ConfigService configService;
    @Autowired private SystemLogService systemLogService;
    @Autowired private RoleService roleService;
    @Autowired private CategoryService categoryService;

    @GetMapping("/sys/notification")
    public ModelAndView getAllNotification() {
        return baseView(new ModelAndView(PagesUtils.SYS_NOTIFICATION));
    }

    @GetMapping("/sys/log")
    public ModelAndView showPageLog() {
        validateModuleSystem.readLog(true);
        return baseView(new ModelAndView(PagesUtils.SYS_LOG));
    }

    @GetMapping("/sys/config")
    public ModelAndView showConfig() {
        validateModuleSystem.setupConfig(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_CONFIG);
        modelAndView.addObject("listConfig", configService.findAll());
        return baseView(modelAndView);
    }

    @PostMapping("/sys/config/update/{id}")
    public ModelAndView update(@ModelAttribute("config") SystemConfig config, @PathVariable("id") Integer configId) {
        validateModuleSystem.setupConfig(true);
        if (configId <= 0 || configService.findById(configId) == null) {
            throw new NotFoundException("Config not found!");
        }
        configService.update(config, configId);
        return new ModelAndView("redirect:/he-thong/config");
    }

    @GetMapping("/sys/role")
    public ModelAndView readRole() {
        validateModuleSystem.readPermission(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_ROLE);
        modelAndView.addObject("listRole", roleService.findAllRole());
        return baseView(modelAndView);
    }
}