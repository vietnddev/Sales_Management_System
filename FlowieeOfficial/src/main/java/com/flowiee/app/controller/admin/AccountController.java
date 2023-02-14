package com.flowiee.app.controller.admin;

import com.flowiee.app.model.admin.Account;
import com.flowiee.app.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.util.List;

@Controller
@RequestMapping(path = "/admin/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping(value = "")
    public String getAccounts(ModelMap modelMap){
        modelMap.addAttribute("listAccount", accountService.getAllAccount());
        return "admin/account";
    }


}
