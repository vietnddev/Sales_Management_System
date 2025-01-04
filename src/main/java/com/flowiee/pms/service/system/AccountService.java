package com.flowiee.pms.service.system;

import com.flowiee.pms.base.service.BaseCurdService;
import com.flowiee.pms.entity.system.Account;

import javax.servlet.http.HttpServletRequest;

public interface AccountService extends BaseCurdService<Account> {
    Account findByUsername(String username);

    Account getUserByResetTokens(String token);

    void updateTokenForResetPassword(String email, String resetToken);

    boolean sendTokenForResetPassword(String email, HttpServletRequest request);

    boolean resetPasswordWithToken(String token, String newPassword);
}