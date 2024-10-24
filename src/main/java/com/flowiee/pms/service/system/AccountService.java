package com.flowiee.pms.service.system;

import com.flowiee.pms.service.BaseCurdService;
import com.flowiee.pms.entity.system.Account;

public interface AccountService extends BaseCurdService<Account> {
    Account findByUsername(String username);

    Account getUserByResetTokens(String token);

    void updateTokenForResetPassword(String email, String resetToken);

    void resetPassword(Account account);
}