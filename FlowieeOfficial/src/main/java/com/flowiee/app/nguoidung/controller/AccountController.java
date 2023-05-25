package com.flowiee.app.nguoidung.controller;

import com.flowiee.app.log.model.SystemLogAction;
import com.flowiee.app.nguoidung.entity.TaiKhoan;
import com.flowiee.app.log.entity.SystemLog;
import com.flowiee.app.nguoidung.service.AccountService;
import com.flowiee.app.log.service.SystemLogService;
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
@RequestMapping(path = "/admin/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SystemLogService systemLogService;

    @GetMapping(value = "")
    public String getAccounts(HttpServletRequest request, ModelMap modelMap) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            modelMap.addAttribute("account", new TaiKhoan());
            modelMap.addAttribute("listAccount", accountService.getAll());

            return "pages/admin/account";
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @Transactional
    @GetMapping(value = "/{ID}")
    public String getDetailAccount(HttpServletRequest request, @PathVariable int ID) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {

            return "";
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @PostMapping(value = "/insert")
    public String save(HttpServletRequest request, @ModelAttribute("account") TaiKhoan accountEntity) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
            String password = accountEntity.getPassword();
            accountEntity.setPassword(bCrypt.encode(password));

            accountService.saveAccount(accountEntity);

            SystemLog systemLog = SystemLog.builder()
                .module("Tài khoản hệ thống")
                .action(SystemLogAction.THEM_MOI.name())
                .noiDung(accountEntity.toString())
                .taiKhoan(TaiKhoan.builder().id(accountService.findIdByUsername(username)).build())
                .ip(IPUtil.getClientIpAddress(request))
                .build();
            systemLogService.writeLog(systemLog);

            return "redirect:/admin/account";
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @PostMapping(value = "/update/{ID}")
    public String update(HttpServletRequest request, @ModelAttribute("account") TaiKhoan accountEntity, @PathVariable("ID") int id) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            Optional<TaiKhoan> acc = accountService.getAccountByID(id);
            if (acc.isPresent()) {
                accountEntity.setId(id);
                accountEntity.setUsername(acc.get().getUsername());
                accountEntity.setPassword(acc.get().getPassword());
                accountEntity.setUpdatedBy(username);
                accountService.saveAccount(accountEntity);
                //Ghi log
                SystemLog systemLog = SystemLog.builder()
                    .module("Tài khoản hệ thống")
                    .action(SystemLogAction.CAP_NHAT.name())
                    .noiDung(accountEntity.toString())
                    .taiKhoan(TaiKhoan.builder().id(accountService.findIdByUsername(username)).build())
                    .ip(IPUtil.getClientIpAddress(request))
                    .build();
                systemLogService.writeLog(systemLog);
                return "redirect:/admin/account";
            } else {
                return "redirect:/admin/account";
            }
        }
        return PagesUtil.PAGE_LOGIN;
    }

    @Transactional
    @PostMapping(value = "/delete/{ID}")
    public String deleteAccount(HttpServletRequest request, @PathVariable int ID) {
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()) {
            TaiKhoan account = accountService.getAccountByID(ID).get();
            accountService.deleteAccountByID(ID);
            //Ghi log
            SystemLog systemLog = SystemLog.builder()
                .module("Tài khoản hệ thống")
                .action(SystemLogAction.XOA.name())
                .noiDung(account.toString())
                .taiKhoan(TaiKhoan.builder().id(accountService.findIdByUsername(username)).build())
                .ip(IPUtil.getClientIpAddress(request))
                .build();
            systemLogService.writeLog(systemLog);

            return "redirect:/admin/account";
        }
        return PagesUtil.PAGE_LOGIN;
    }
}