package com.flowiee.app.base;

import com.flowiee.app.exception.AuthenticationException;
import com.flowiee.app.service.RoleService;
import com.flowiee.app.utils.CommonUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BaseAuthorize {
    @Autowired
    private RoleService roleService;

    @SneakyThrows
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new AuthenticationException("Please login!");
        }
        return authentication.isAuthenticated();
    }

    protected boolean isAuthorized(String module, String action) {
        if (isAuthenticated()) {
            if (CommonUtil.ADMINISTRATOR.equals(CommonUtil.getCurrentAccountUsername())) {
                return true;
            }
            if (roleService.isAuthorized(Objects.requireNonNull(CommonUtil.getCurrentAccountId()), module, action)) {
                return true;
            } else {
                //throw new ForbiddenException("403!");
            }
        }
        return false;
    }
}