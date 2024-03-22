package com.flowiee.pms.controller.system;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.service.system.ConfigService;
import com.flowiee.pms.utils.PagesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sys")
public class SystemControllerView extends BaseController {
    @Autowired private ConfigService configService;

    @GetMapping("/notification")
    public ModelAndView getAllNotification() {
        return baseView(new ModelAndView(PagesUtils.SYS_NOTIFICATION));
    }

    @GetMapping("/log")
    @PreAuthorize("@vldModuleSystem.readLog(true)")
    public ModelAndView showPageLog() {
        return baseView(new ModelAndView(PagesUtils.SYS_LOG));
    }

    @GetMapping("/config")
    @PreAuthorize("@vldModuleSystem.setupConfig(true)")
    public ModelAndView showConfig() {
        ModelAndView modelAndView = new ModelAndView(PagesUtils.SYS_CONFIG);
        modelAndView.addObject("listConfig", configService.findAll());
        return baseView(modelAndView);
    }

    @PostMapping("/config/update/{id}")
    @PreAuthorize("@vldModuleSystem.setupConfig(true)")
    public ModelAndView update(@ModelAttribute("config") SystemConfig config, @PathVariable("id") Integer configId) {
        if (configId <= 0 || configService.findById(configId).isEmpty()) {
            throw new NotFoundException("Config not found!");
        }
        configService.update(config, configId);
        return new ModelAndView("redirect:/he-thong/config");
    }
}