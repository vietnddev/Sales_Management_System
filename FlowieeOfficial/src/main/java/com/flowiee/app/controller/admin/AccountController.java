package com.flowiee.app.controller.admin;

import com.flowiee.app.model.admin.Account;
import com.flowiee.app.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/admin/account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping(value = "")
    public String getAccounts(ModelMap modelMap){
        modelMap.addAttribute("listAccount", accountService.getAll());
        return "pages/admin/account";
    }

    @GetMapping(value = "/detail-{ID}")
    @ResponseBody
    public Optional<Account> getDetailAccount(@PathVariable int ID){
        System.out.println(accountService.getAccountByID(ID));
        return Optional.of(accountService.getAccountByID(ID).get());
    }

    @RequestMapping(value = "/delete-{ID}", method = RequestMethod.POST)
    @ResponseBody
    public void deleteAccount(@PathVariable int ID){
        accountService.deleteAccountByID(ID);
    }
}
