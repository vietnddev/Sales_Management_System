package com.flowiee.app.controller;

import com.flowiee.app.model.admin.Account;
import com.flowiee.app.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String homePage() {

        return "home";
    }
}
