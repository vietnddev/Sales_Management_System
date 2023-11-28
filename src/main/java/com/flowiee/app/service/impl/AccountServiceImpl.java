package com.flowiee.app.service.impl;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.FlowieeUtil;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.SystemLog;
import com.flowiee.app.model.role.Role;
import com.flowiee.app.model.role.SystemAction.SysAction;
import com.flowiee.app.repository.AccountRepository;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.RoleService;
import com.flowiee.app.service.SystemLogService;
import com.flowiee.app.model.role.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private RoleService roleService;

    @Override
    public List<Account> findAll() {
        List<Account> listAccountReturn = new ArrayList<>();
        for (Account account : accountRepository.findAll()) {
            account.setListRole(roleService.findAllRole());
            for (Role role : account.getListRole()) {
                String module = role.getModule().keySet().toString().replaceAll("\\[|\\]", "").replaceAll("\"", "");
                if (role.getAction() != null) {
                    for (Role.Action act : role.getAction()) {
                        if (roleService.isAuthorized(account.getId(), module, act.getKeyAction())) {
                            act.setChecked(true);
                        }
                    }
                }
            }
            listAccountReturn.add(account);
        }
        return listAccountReturn;
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account findById(Integer accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public int findIdByUsername(String username) {
        return accountRepository.findIdByUsername(username);
    }

    @Override
    public Account findCurrentAccount() {
        Account account = null;
        WebAuthenticationDetails details = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object authDetails = authentication.getDetails();
            if (authDetails instanceof WebAuthenticationDetails) {
                details = (WebAuthenticationDetails) authDetails;
            }
            account = this.findByUsername(authentication.getName());
            account.setIp(details != null ? details.getRemoteAddress() : "unknown");
        }
        return account;
    }

    @Override
    public String findCurrentAccountUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName().substring(0, authentication.getName().indexOf("_"));
    }

    @Override
    public String findCurrentAccountIp() {
        WebAuthenticationDetails details = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object authDetails = authentication.getDetails();
            if (authDetails instanceof WebAuthenticationDetails) {
                details = (WebAuthenticationDetails) authDetails;
            }
        }
        return details != null ? details.getRemoteAddress() : "unknown";
    }

    @Override
    public Integer findCurrentAccountId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Integer.parseInt(authentication.getName().substring(authentication.getName().indexOf("_") + 1));
    }

    @Override
    public String save(Account account) {
        if (account.getRole() != null && account.getRole().equals(FlowieeUtil.ADMINISTRATOR)) {
            account.setRole("ADMIN");
        } else {
            account.setRole("USER");
        }
        accountRepository.save(account);
    	SystemLog systemLog = new SystemLog(SystemModule.SYSTEM.name(), SysAction.SYS_ACCOUNT_CREATE.name(), "", null, this.findCurrentAccountId(), this.findCurrentAccountIp());
        systemLogService.writeLog(systemLog);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(Account account, Integer entityId) {
        account.setId(entityId);
        if (account.getRole() != null && account.getRole().equals(FlowieeUtil.ADMINISTRATOR)) {
            account.setRole("ADMIN");
        } else {
            account.setRole("USER");
        }
        accountRepository.save(account);
    	SystemLog systemLog = new SystemLog(SystemModule.SYSTEM.name(), SysAction.SYS_ACCOUNT_UPDATE.name(), "", null, this.findCurrentAccountId(), this.findCurrentAccountIp());
        systemLogService.writeLog(systemLog);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            accountRepository.delete(account);
            SystemLog systemLog = new SystemLog(SystemModule.SYSTEM.name(), SysAction.SYS_ACCOUNT_DELETE.name(), "", null, this.findCurrentAccountId(), this.findCurrentAccountIp());
            systemLogService.writeLog(systemLog);
        }
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public boolean isLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName() != null;
    }
}