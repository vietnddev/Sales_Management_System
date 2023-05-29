package com.flowiee.app.hethong.controller;

import com.flowiee.app.common.utils.PagesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/he-thong/config")
public class SystemConfigController {

    @GetMapping
    public String showConfig() {
        return PagesUtil.PAGE_UNAUTHORIZED;
    }
}
