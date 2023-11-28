package com.flowiee.app.controller;

import com.flowiee.app.security.author.ValidateModuleSystem;
import com.flowiee.app.entity.FlowieeConfig;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.service.ConfigService;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.utils.PagesUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/he-thong/config")
public class SystemConfigController extends BaseController {
    @Autowired
    private ValidateModuleSystem validateModuleSystem;
    @Autowired
    private ConfigService configService;

    @GetMapping
    public ModelAndView showConfig() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleSystem.setupConfig()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.SYS_CONFIG);
        modelAndView.addObject("config", new FlowieeConfig());
        modelAndView.addObject("listConfig", configService.findAll());        
        return baseView(modelAndView);
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("config") FlowieeConfig config,
                         @PathVariable("id") Integer configId) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleSystem.setupConfig()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (configId <= 0 || configService.findById(configId) == null) {
            throw new NotFoundException("Config not found!");
        }
        configService.update(config, configId);
        return "redirect:/he-thong/config";
    }
}