package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.SystemLog;
import com.flowiee.app.model.system.Role;
import com.flowiee.app.repository.system.AccountRepository;
import com.flowiee.app.service.system.AccountService;
import com.flowiee.app.service.system.RoleService;
import com.flowiee.app.service.system.SystemLogService;
import com.flowiee.app.common.action.AccountAction;
import com.flowiee.app.common.action.SystemAction;
import com.flowiee.app.common.module.SystemModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String save(Account account) {
        if (account.getRole() != null && account.getRole().equals(FlowieeUtil.ADMINISTRATOR)) {
            account.setRole("ADMIN");
        } else {
            account.setRole("USER");
        }
        accountRepository.save(account);
    	SystemLog systemLog = new SystemLog(SystemModule.HE_THONG.name(), AccountAction.CREATE_ACCOUNT.name(), "", null, FlowieeUtil.ACCOUNT_ID, FlowieeUtil.ACCOUNT_IP);
        systemLogService.writeLog(systemLog);
        return TagName.SERVICE_RESPONSE_SUCCESS;
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
    	SystemLog systemLog = new SystemLog(SystemModule.HE_THONG.name(), AccountAction.UPDATE_ACCOUNT.name(), "", null, FlowieeUtil.ACCOUNT_ID, FlowieeUtil.ACCOUNT_IP);
        systemLogService.writeLog(systemLog);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            accountRepository.delete(account);
            SystemLog systemLog = new SystemLog(SystemModule.HE_THONG.name(), AccountAction.DELETE_ACCOUNT.name(), "", null, FlowieeUtil.ACCOUNT_ID, FlowieeUtil.ACCOUNT_IP);
            systemLogService.writeLog(systemLog);
        }
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public boolean isLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName() != null;
    }
}