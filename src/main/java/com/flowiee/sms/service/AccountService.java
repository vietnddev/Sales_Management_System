package com.flowiee.sms.service;

import com.flowiee.sms.core.BaseService;
import com.flowiee.sms.entity.Account;

import java.util.List;

public interface AccountService extends BaseService<Account> {
    List<Account> findAll();

    Account findByUsername(String username);

    Account findCurrentAccount();
}