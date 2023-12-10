package com.flowiee.app.controller;

import com.flowiee.app.utils.PagesUtil;
import com.flowiee.app.entity.Account;
import com.flowiee.app.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/sys/login")
    public String showLoginPage() {
        if (accountService.findByUsername("admin") == null) {
            Account account = new Account();
            account.setUsername("admin");
            account.setPassword("$2a$12$UGPx1eE9SzfvCDniYtwoZuQRzVdjHKkjbZcDKXO4.1Z/uGpOsFFVu");
            account.setHoTen("Quản trị hệ thống");
            account.setEmail("nguyenducviet0684@gmail.com");
            account.setSoDienThoai("0706820684");
            account.setGioiTinh(true);
            account.setTrangThai(true);
            account.setCreatedBy(0);
            account.setRole("ADMIN");
            accountService.save(account);
        }
        return "login";
    }

    @GetMapping(value = "/change-password")
    public String showPageChangePassword() {
        return PagesUtil.SYS_UNAUTHORIZED;
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
