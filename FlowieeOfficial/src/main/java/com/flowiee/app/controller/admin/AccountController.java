package com.flowiee.app.controller.admin;

import com.flowiee.app.model.admin.Account;
import com.flowiee.app.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/admin/account/")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping(value = "")
    @ResponseBody
    public List<Account> getAccount(){
        return accountService.getAllAccount();
    }
}
