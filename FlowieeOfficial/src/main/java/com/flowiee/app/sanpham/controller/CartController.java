package com.flowiee.app.sanpham.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/sales/cart")
public class CartController {
    @GetMapping("")
    public String showCartControl(ModelMap modelMap){
        System.out.println("Hello cart");
        return "pages/sales/cart";
    }
}
