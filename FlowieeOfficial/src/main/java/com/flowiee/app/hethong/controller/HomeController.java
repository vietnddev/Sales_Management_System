package com.flowiee.app.hethong.controller;

import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.hethong.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/login")
    public String showLoginPage(HttpServletRequest request) {
        if (accountService.findByUsername("admin") == null) {
            Account account = new Account();
            account.setUsername("admin");
            account.setPassword("$2a$12$UGPx1eE9SzfvCDniYtwoZuQRzVdjHKkjbZcDKXO4.1Z/uGpOsFFVu");
            account.setHoTen("Quản trị hệ thống");
            account.setEmail("nguyenducviet0684@gmail.com");
            account.setSoDienThoai("0706820684");
            account.setGioiTinh(true);
            account.setTrangThai(true);
            account.setCreatedBy("System");
            accountService.save(account);
        }
        return "login";
    }

    @GetMapping(value = "/change-password")
    public String showPageChangePassword() {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        return PagesUtil.PAGE_UNAUTHORIZED;
    }
    @GetMapping(value = "/change-password", params = "submit")
    public void submitChangePassword(){
        //
    }

    @GetMapping(value = "/forgot-password")
    public void showPageForgotPassword(){
        //Send password to email register
    }

    @GetMapping(value = "/reset-password")
    public void resetPassword(){
        //Send password to email register
    }
}
