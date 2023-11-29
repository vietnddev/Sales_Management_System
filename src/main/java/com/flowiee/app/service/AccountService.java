package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Account;

public interface AccountService extends BaseService<Account> {
    Account findByUsername(String username);

    int findIdByUsername(String username);

    Account findCurrentAccount();

    boolean isLogin();
}