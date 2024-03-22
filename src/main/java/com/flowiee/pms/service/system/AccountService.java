package com.flowiee.pms.service.system;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.entity.system.Account;

public interface AccountService extends BaseService<Account> {
    Account findByUsername(String username);
}