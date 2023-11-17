package com.flowiee.app.controller.system;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.config.author.ValidateModuleSystem;
import com.flowiee.app.exception.DataExistsException;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.entity.Account;
import com.flowiee.app.model.system.ActionOfModule;
import com.flowiee.app.model.system.FlowieeRole;
import com.flowiee.app.model.system.Role;
import com.flowiee.app.service.system.AccountService;
import com.flowiee.app.service.system.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/he-thong/tai-khoan")
public class AccountController extends BaseController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ValidateModuleSystem validateModuleSystem;

    @GetMapping(value = "")
    public ModelAndView findAllAccount() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (!validateModuleSystem.readAccount()) {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_HETHONG_TAIKHOAN_LIST);
        modelAndView.addObject("account", new Account());
        modelAndView.addObject("listAccount", accountService.findAll());
        List<Role> newRole = new ArrayList<>();
        modelAndView.addObject("list", newRole);
        return baseView(modelAndView);
    }

    @GetMapping(value = "/{id}")
    public ModelAndView findDetailAccountById(@PathVariable("id") Integer accountId) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (!validateModuleSystem.readAccount()) {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
        if (accountId <= 0 || accountService.findById(accountId) == null) {
            throw new NotFoundException("Account not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_HETHONG_TAIKHOAN_DETAIL);
        List<FlowieeRole> roleOfAccount = roleService.findAllRoleByAccountId(accountId);
        modelAndView.addObject("listRole", roleOfAccount);
        modelAndView.addObject("accountInfo", accountService.findById(accountId));
        return baseView(modelAndView);
    }

    @PostMapping(value = "/insert")
    public String save(@ModelAttribute("account") Account account) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (!validateModuleSystem.insertAccount()) {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
        if (accountService.findByUsername(account.getUsername()) != null) {
            throw new DataExistsException("Username exists!");
        }
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        String password = account.getPassword();
        account.setPassword(bCrypt.encode(password));
        accountService.save(account);
        return "redirect:/he-thong/tai-khoan";
    }

    @PostMapping(value = "/update/{id}")
    public String update(@ModelAttribute("account") Account accountEntity,
                         @PathVariable("id") Integer accountId,
                         HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (!validateModuleSystem.updateAccount()) {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
        if (accountId <= 0 || accountService.findById(accountId) == null) {
            throw new NotFoundException("Account not found!");
        }
        Account acc = accountService.findById(accountId);
        accountEntity.setId(accountId);
        accountEntity.setUsername(acc.getUsername());
        accountEntity.setPassword(acc.getPassword());
        accountEntity.setLastUpdatedBy(FlowieeUtil.ACCOUNT_USERNAME);
        accountService.update(accountEntity, accountId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteAccount(@PathVariable Integer accountId) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (!validateModuleSystem.deleteAccount()) {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
        if (accountId <= 0 ||accountService.findById(accountId) == null) {
            throw new NotFoundException("Account not found!");
        }
        Account account = accountService.findById(accountId);
        account.setTrangThai(false);
        accountService.save(account);
        return "redirect:/he-thong/tai-khoan";
    }

    @PostMapping("/update-permission/{id}")
    public String updatePermission(@PathVariable("id") Integer accountId, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (!validateModuleSystem.updateAccount()) {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
        if (accountId <= 0 || accountService.findById(accountId) == null) {
            throw new NotFoundException("Account not found!");
        }
        roleService.deleteAllRole(accountId);
        List<ActionOfModule> listAction = roleService.findAllAction();
        for (int i = 0; i < listAction.size(); i++) {
            ActionOfModule sysAction = listAction.get(i);
            String clientActionKey = request.getParameter(sysAction.getActionKey());
            if (clientActionKey != null) {
                Boolean isAuthorSelected = clientActionKey.equals("on") ? true : false;
                if (isAuthorSelected) {
                    roleService.updatePermission(sysAction.getModuleKey(), sysAction.getActionKey(), accountId);
                }
            }
        }
        return "redirect:/he-thong/tai-khoan/" + accountId;
    }
}