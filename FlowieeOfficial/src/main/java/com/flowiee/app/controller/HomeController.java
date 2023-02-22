package com.flowiee.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "")
public class HomeController {

    @RequestMapping(value = "")
    public String homePage() {
        return "pages/sales/customer";
    }

    @GetMapping(value = "/login")
    public void showLoginPage(){
        //
    }

    @GetMapping(value = "/login", params = "submit")
    public void submitLogin(){
        //
    }

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
