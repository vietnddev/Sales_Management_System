package com.flowiee.pms.service.system;

import com.flowiee.pms.base.service.BaseCurdService;
import com.flowiee.pms.entity.system.Account;

public interface AccountService extends BaseCurdService<Account> {
    Account findByUsername(String username);
}