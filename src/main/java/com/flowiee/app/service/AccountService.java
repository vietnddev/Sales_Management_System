package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.Account;

import java.util.List;

public interface AccountService extends BaseService<Account> {
    List<Account> findAll();

    Account findByUsername(String username);

    Account findCurrentAccount();
}