package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.system.Account;
import com.flowiee.app.entity.system.SystemLog;
import com.flowiee.app.model.system.Role;
import com.flowiee.app.repository.system.AccountRepository;
import com.flowiee.app.service.system.AccountService;
import com.flowiee.app.service.system.RoleService;
import com.flowiee.app.service.system.SystemLogService;
import com.flowiee.app.common.action.AccountAction;
import com.flowiee.app.common.module.SystemModule;

import org.springframework.beans.factory.annotation.Autowired;
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
            account.setRole(roleService.findAllRole());
            for (Role role : account.getRole()) {
                String module = role.getModule().keySet().toString().replaceAll("\\[|\\]", "").replaceAll("\"", "");;
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
        Account accountSaved = accountRepository.save(account);
        //Ghi log
        SystemLog systemLog = SystemLog.builder()
            .module(SystemModule.HE_THONG.name())
            .action(AccountAction.CREATE_ACCOUNT.name())
            .noiDung(account.toString())
            .account(new Account(accountRepository.findIdByUsername(account.getUsername())))
            .ip(FlowieeUtil.ACCOUNT_IP)
            .build();
        systemLogService.writeLog(systemLog);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(Account entity, Integer entityId) {
        Account accountSaved = accountRepository.save(entity);
        //Ghi log
        SystemLog systemLog = SystemLog.builder()
                .module(SystemModule.HE_THONG.name())
                .action(AccountAction.UPDATE_ACCOUNT.name())
                .noiDung(entity.toString())
                .account(new Account(accountRepository.findIdByUsername(entity.getUsername())))
                .ip(FlowieeUtil.ACCOUNT_IP)
                .build();
        systemLogService.writeLog(systemLog);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            accountRepository.delete(account);
            //Ghi log
            SystemLog systemLog = SystemLog.builder()
                .module(SystemModule.HE_THONG.name())
                .action(AccountAction.DELETE_ACCOUNT.name())
                .noiDung(account.toString())
                .account(FlowieeUtil.ACCOUNT)
                .ip(FlowieeUtil.ACCOUNT_IP)
                .build();
            systemLogService.writeLog(systemLog);
        }
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public boolean isLogin() {
        Account account = FlowieeUtil.ACCOUNT;
        return account != null ? true : false;
    }
}