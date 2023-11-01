package com.flowiee.app.system.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.system.entity.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService extends BaseService<Account> {
    Account findByUsername(String username);

    int findIdByUsername(String username);

    boolean isLogin();
}