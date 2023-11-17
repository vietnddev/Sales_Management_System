package com.flowiee.app.controller.system;

import com.flowiee.app.config.KiemTraQuyenModuleAdministrator;
import com.flowiee.app.entity.FlowieeConfig;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.service.system.AccountService;
import com.flowiee.app.service.system.FlowieeConfigService;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.utils.PagesUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/he-thong/config")
public class SystemConfigController extends BaseController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private KiemTraQuyenModuleAdministrator kiemTraQuyenModuleAdministrator;
    @Autowired
    private FlowieeConfigService flowieeConfigService;

    @GetMapping
    public ModelAndView showConfig() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (!kiemTraQuyenModuleAdministrator.kiemTraQuyenConfig()) {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_HETHONG_CONFIG);
        modelAndView.addObject("config", new FlowieeConfig());
        modelAndView.addObject("listConfig", flowieeConfigService.findAll());        
        return baseView(modelAndView);
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("config") FlowieeConfig config,
                         @PathVariable("id") Integer configId) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (!kiemTraQuyenModuleAdministrator.kiemTraQuyenConfig()) {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
        if (configId <= 0 || flowieeConfigService.findById(configId) == null) {
            throw new NotFoundException("Config not found!");
        }
        flowieeConfigService.update(config, configId);
        return "redirect:/he-thong/config";
    }
}