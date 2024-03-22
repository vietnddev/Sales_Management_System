package com.flowiee.pms.controller.system;

import com.flowiee.pms.utils.EndPointUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginControllerView {
    @GetMapping(EndPointUtil.SYS_LOGIN)
    public ModelAndView showLoginPage() {
        return new ModelAndView("login");
    }
}