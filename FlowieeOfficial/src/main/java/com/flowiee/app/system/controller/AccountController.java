package com.flowiee.app.system.controller;

import com.flowiee.app.common.exception.DataExistsException;
import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.system.model.ActionOfModule;
import com.flowiee.app.system.model.FlowieeRole;
import com.flowiee.app.system.model.Role;
import com.flowiee.app.system.entity.Account;
import com.flowiee.app.system.service.AccountService;
import com.flowiee.app.system.service.NotificationService;
import com.flowiee.app.system.service.RoleService;
import com.flowiee.app.system.service.SystemLogService;
import com.flowiee.app.common.utils.PagesUtil;
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
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "")
    public ModelAndView findAllAccount() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_HETHONG_TAIKHOAN_LIST);
        modelAndView.addObject("account", new Account());
        modelAndView.addObject("listAccount", accountService.findAll());
        modelAndView.addObject("listNotification", notificationService.findAllByReceiveId(FlowieeUtil.ACCOUNT_ID));
        List<Role> newRole = new ArrayList<>();
        modelAndView.addObject("list", newRole);
        return modelAndView;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView findDetailAccountById(@PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_HETHONG_TAIKHOAN_DETAIL);
        //Role
        List<FlowieeRole> roleOfAccount = roleService.findAllRoleByAccountId(id);
        modelAndView.addObject("listRole", roleOfAccount);
        //Account info
        Account accountInfo = accountService.findById(id);
        modelAndView.addObject("accountInfo", accountInfo);
        return modelAndView;
    }

    @PostMapping(value = "/insert")
    public ModelAndView save(HttpServletRequest request, @ModelAttribute("account") Account account) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (accountService.findByUsername(account.getUsername()) != null) {
            throw new DataExistsException();
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/he-thong/tai-khoan");
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        String password = account.getPassword();
        account.setPassword(bCrypt.encode(password));
        accountService.save(account);
        return modelAndView;
    }

    @PostMapping(value = "/update/{id}")
    public String update(@ModelAttribute("account") Account accountEntity,
                         @PathVariable("id") int id,
                         HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        Account acc = accountService.findById(id);
        if (acc != null) {
            accountEntity.setId(id);
            accountEntity.setUsername(acc.getUsername());
            accountEntity.setPassword(acc.getPassword());
            accountEntity.setLastUpdatedBy(FlowieeUtil.ACCOUNT_USERNAME);
            accountService.update(accountEntity, id);
        }
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteAccount(@PathVariable int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (accountService.findById(id) == null) {
            throw new NotFoundException();
        }
        Account account = accountService.findById(id);
        account.setTrangThai(false);
        accountService.save(account);
        return "redirect:/he-thong/tai-khoan";
    }

    @PostMapping("/update-permission/{id}")
    public String updatePermission(@PathVariable("id") Integer accountId, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (accountService.findById(accountId) == null) {
            throw new NotFoundException();
        }
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/he-thong/tai-khoan/" + accountId;
    }
}