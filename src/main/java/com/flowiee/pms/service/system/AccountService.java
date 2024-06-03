package com.flowiee.pms.service.system;

import com.flowiee.pms.service.BaseCurd;
import com.flowiee.pms.entity.system.Account;

public interface AccountService extends BaseCurd<Account> {
    Account findByUsername(String username);
}