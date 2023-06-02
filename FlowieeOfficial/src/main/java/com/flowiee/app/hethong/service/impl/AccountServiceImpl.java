package com.flowiee.app.hethong.service.impl;

import com.flowiee.app.common.utils.IPUtil;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.hethong.entity.SystemLog;
import com.flowiee.app.hethong.model.SystemLogAction;
import com.flowiee.app.hethong.model.action.AccountAction;
import com.flowiee.app.hethong.model.module.SystemModule;
import com.flowiee.app.hethong.repository.AccountRepository;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private SystemLogService systemLogService;

    @Override
    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account findById(int ID) {
        return accountRepository.findById(ID).orElse(null);
    }

    @Override
    public int findIdByUsername(String username) {
        return accountRepository.findIdByUsername(username);
    }

    @Override
    public Account save(Account account) {
        Account accountSaved = accountRepository.save(account);
        //Ghi log
        SystemLog systemLog = SystemLog.builder()
            .module(SystemModule.HE_THONG.name())
            .action(AccountAction.CREATE_ACCOUNT.name())
            .noiDung(account.toString())
            .account(Account.builder().id(accountRepository.findIdByUsername(account.getUsername())).build())
            .ip(getIP())
            .build();
        systemLogService.writeLog(systemLog);
        return accountSaved;
    }

    @Override
    public Account update(Account account) {
        Account accountSaved = accountRepository.save(account);
        //Ghi log
        SystemLog systemLog = SystemLog.builder()
            .module(SystemModule.HE_THONG.name())
            .action(AccountAction.UPDATE_ACCOUNT.name())
            .noiDung(account.toString())
            .account(Account.builder().id(accountRepository.findIdByUsername(account.getUsername())).build())
            .ip(getIP())
            .build();
        systemLogService.writeLog(systemLog);
        return accountSaved;
    }

    @Override
    public void delete(int ID) {
        Account account = accountRepository.findById(ID).orElse(null);
        if (account != null) {
            accountRepository.delete(account);
            //Ghi log
            SystemLog systemLog = SystemLog.builder()
                .module(SystemModule.HE_THONG.name())
                .action(AccountAction.DELETE_ACCOUNT.name())
                .noiDung(account.toString())
                .account(Account.builder().id(accountRepository.findIdByUsername(account.getUsername())).build())
                .ip(getIP())
                .build();
            systemLogService.writeLog(systemLog);
        }
    }

    @Override
    public String getUserName() {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username.toLowerCase();
    }

    @Override
    public String getUserNameByID(int id) {
        return accountRepository.findUsernameById(id);
    }

    @Override
    public String getIP() {
        WebAuthenticationDetails details = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object authDetails = authentication.getDetails();
            if (authDetails instanceof WebAuthenticationDetails) {
                details = (WebAuthenticationDetails) authDetails;
            }
        }
        if (details != null){
            return details.getRemoteAddress();
        }
        return "unknown";
    }
}