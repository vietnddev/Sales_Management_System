package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.sanpham.services.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/khach-hang")
public class CustomerController {
    @Autowired
    KhachHangService customerService;

    @GetMapping(value = "")
    public String getAllCustomer(ModelMap modelMap){
        modelMap.addAttribute("listCustomer", customerService.getAll());
        return PagesUtil.PAGE_KHACHHANG;
    }
}