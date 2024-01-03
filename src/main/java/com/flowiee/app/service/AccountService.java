package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Account;

public interface AccountService extends BaseService<Account> {
    Account findByUsername(String username);

    Account findCurrentAccount();
}