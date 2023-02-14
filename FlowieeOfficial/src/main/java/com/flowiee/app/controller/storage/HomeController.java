package com.flowiee.app.controller.storage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/category")
public class HomeController {
    @GetMapping(value = "")
    public String getAllCategory(ModelMap modelMap){
        //
        return "pages/category/home";
    }
}
