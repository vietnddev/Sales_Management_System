package com.flowiee.app.controller.admin;

import com.flowiee.app.services.DocShare2Service;
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
    public String getLogLogin(ModelMap modelMap){
        String action = "Đăng nhập hệ thống";
        modelMap.addAttribute("listLog", logService.getAll());
        return "pages/admin/log";
    }

    @GetMapping(value = "/access")
    public String getLogAccess(ModelMap modelMap){
        String action = "Truy cập chức năng";
        modelMap.addAttribute("listLog", logService.getByAction(action));
        return "pages/admin/log";
    }

    @GetMapping(value = "/modify")
    public String getAllLog(ModelMap modelMap){
        String action = "Cập nhật dữ liệu";
        modelMap.addAttribute("listLog", logService.getByAction(action));
        return "pages/admin/log";
    }
}
