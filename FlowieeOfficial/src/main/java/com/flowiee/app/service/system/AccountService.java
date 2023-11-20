package com.flowiee.app.service.system;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Account;

import org.springframework.stereotype.Service;

@Service
public interface AccountService extends BaseService<Account> {
    Account findByUsername(String username);

    int findIdByUsername(String username);

    Account findCurrentAccount();

    String findCurrentAccountUsername();

    String findCurrentAccountIp();

    Integer findCurrentAccountId();

    boolean isLogin();
}