package com.flowiee.pms.service.system;

import com.flowiee.pms.service.CrudService;
import com.flowiee.pms.entity.system.Account;

public interface AccountService extends CrudService<Account> {
    Account findByUsername(String username);
}