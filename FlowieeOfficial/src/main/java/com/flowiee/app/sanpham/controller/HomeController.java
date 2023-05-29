package com.flowiee.app.sanpham.controller;

import com.flowiee.app.account.entity.Account;
import com.flowiee.app.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "")
public class HomeController {
    @Autowired
    private AccountService accountService;

//    @RequestMapping(value = "")
//    public String homePage() {
//        return "pages/sales/product";
//    }

    @GetMapping(value = "/login")
    public String showLoginPage(HttpServletRequest request){
        if (accountService.getAccountByUsername("admin") == null){
            Account account = new Account();
            account.setUsername("admin");
            account.setPassword("$2a$12$UGPx1eE9SzfvCDniYtwoZuQRzVdjHKkjbZcDKXO4.1Z/uGpOsFFVu");
            account.setHoTen("Quản trị hệ thống");
            account.setEmail("nguyenducviet0684@gmail.com");
            account.setSoDienThoai("0706820684");
            account.setGioiTinh(true);
            account.setTrangThai(true);
            account.setCreatedBy("System");
            accountService.saveAccount(account);
        }
        return "login";
    }

//    @GetMapping(value = "/login", params = "submit")
//    public void submitLogin(){
//        //
//    }

    @GetMapping(value = "/change-password")
    public void showPageChangePassword(){
        //
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
