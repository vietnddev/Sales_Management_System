package com.flowiee.app.controller.admin;

import com.flowiee.app.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin/log")
public class LogController {
    @Autowired
    LogService logService;

    @GetMapping(value = "")
    public String getLogAccess(ModelMap modelMap){
        String action = "access";
        modelMap.addAttribute("listLog", logService.getByAction(action));
        return "pages/admin/log";
    }

    @GetMapping(value = "/login")
    public String getLogLogin(ModelMap modelMap){
        String action = "login";
        modelMap.addAttribute("listLog", logService.getByAction(action));
        return "pages/admin/log";
    }
}
