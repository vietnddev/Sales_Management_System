package com.flowiee.app.hethong.controller;

import com.flowiee.app.authorization.KiemTraQuyenModuleAdministrator;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.hethong.entity.FlowieeConfig;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.FlowieeConfigService;
import com.flowiee.app.hethong.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/he-thong/config")
public class SystemConfigController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private KiemTraQuyenModuleAdministrator kiemTraQuyenModuleAdministrator;
    @Autowired
    private FlowieeConfigService flowieeConfigService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ModelAndView showConfig() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (!kiemTraQuyenModuleAdministrator.kiemTraQuyenConfig()) {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
        List<FlowieeConfig> listConfig = flowieeConfigService.findAll();
        if (listConfig.isEmpty()) {
            flowieeConfigService.defaultConfig();
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_HETHONG_CONFIG);
        modelAndView.addObject("config", new FlowieeConfig());
        modelAndView.addObject("listConfig", flowieeConfigService.findAll());
        modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("config") FlowieeConfig config,
                         @PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (!kiemTraQuyenModuleAdministrator.kiemTraQuyenConfig()) {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
        flowieeConfigService.update(config, id);
        return "redirect:/he-thong/config";
    }
}