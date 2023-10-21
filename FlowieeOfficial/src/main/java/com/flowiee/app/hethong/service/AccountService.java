package com.flowiee.app.hethong.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.hethong.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService extends BaseService<Account> {
    Account findByUsername(String username);

    int findIdByUsername(String username);

    boolean isLogin();
}