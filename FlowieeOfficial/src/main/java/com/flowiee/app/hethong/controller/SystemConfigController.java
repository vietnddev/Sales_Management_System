package com.flowiee.app.hethong.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleAdministrator;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.hethong.entity.CauHinhHeThong;
import com.flowiee.app.hethong.service.AccountService;
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

    @GetMapping
    public ModelAndView showConfig() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (!kiemTraQuyenModuleAdministrator.kiemTraQuyenConfig()) {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
        List<CauHinhHeThong> listConfig = flowieeConfigService.findAll();
        if (listConfig.isEmpty()) {
            flowieeConfigService.defaultConfig();
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_HETHONG_CONFIG);
        modelAndView.addObject("config", new CauHinhHeThong());
        modelAndView.addObject("listConfig", flowieeConfigService.findAll());
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("config") CauHinhHeThong config,
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