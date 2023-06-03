package com.flowiee.app.hethong.controller;

import com.flowiee.app.common.exception.DataExistsException;
import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.hethong.model.SystemLogAction;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.hethong.entity.SystemLog;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.RoleService;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.common.utils.IPUtil;
import com.flowiee.app.common.utils.PagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping(path = "/he-thong/tai-khoan")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "")
    public String findAll(HttpServletRequest request, ModelMap modelMap) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            modelMap.addAttribute("account", new Account());
            modelMap.addAttribute("listAccount", accountService.findAll());
            modelMap.addAttribute("listRole", roleService.findAllRole());
            return PagesUtil.PAGE_HETHONG_TAIKHOAN;
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @Transactional
    @GetMapping(value = "/{id}")
    public String getDetailAccount(HttpServletRequest request, @PathVariable int id) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {

            return "";
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @PostMapping(value = "/insert")
    public String save(HttpServletRequest request, @ModelAttribute("account") Account account) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            if (accountService.findByUsername(account.getUsername()) != null) {
                throw new DataExistsException();
            }
            BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
            String password = account.getPassword();
            account.setPassword(bCrypt.encode(password));
            accountService.save(account);

            SystemLog systemLog = SystemLog.builder()
                .module("Tài khoản hệ thống")
                .action(SystemLogAction.THEM_MOI.name())
                .noiDung(account.toString())
                .account(Account.builder().id(accountService.findIdByUsername(username)).build())
                .ip(IPUtil.getClientIpAddress(request))
                .build();
            systemLogService.writeLog(systemLog);

            return "redirect:/he-thong/tai-khoan";
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @PostMapping(value = "/update/{id}")
    public String update(HttpServletRequest request, @ModelAttribute("account") Account accountEntity, @PathVariable("id") int id) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            Account acc = accountService.findById(id);
            if (acc != null) {
                accountEntity.setId(id);
                accountEntity.setUsername(acc.getUsername());
                accountEntity.setPassword(acc.getPassword());
                accountEntity.setUpdatedBy(username);
                accountService.update(accountEntity);
                //Ghi log
                SystemLog systemLog = SystemLog.builder()
                    .module("Tài khoản hệ thống")
                    .action(SystemLogAction.CAP_NHAT.name())
                    .noiDung(accountEntity.toString())
                    .account(Account.builder().id(accountService.findIdByUsername(username)).build())
                    .ip(IPUtil.getClientIpAddress(request))
                    .build();
                systemLogService.writeLog(systemLog);
                return "redirect:/he-thong/tai-khoan";
            } else {
                return "redirect:/he-thong/tai-khoan";
            }
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @Transactional
    @PostMapping(value = "/delete/{id}")
    public String deleteAccount(@PathVariable int id) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            if (accountService.findById(id) == null) {
                throw new NotFoundException();
            }
            Account account = accountService.findById(id);
            account.setTrangThai(false);
            accountService.save(account);
            return "redirect:/he-thong/tai-khoan";
        }
        return PagesUtil.PAGE_LOGIN;
    }
}