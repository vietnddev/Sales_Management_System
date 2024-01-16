package com.flowiee.app.base;

import com.flowiee.app.exception.AuthenticationException;
import com.flowiee.app.exception.ForbiddenException;
import com.flowiee.app.service.RoleService;
import com.flowiee.app.utils.CommonUtils;
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
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new AuthenticationException();
        }
        return authentication.isAuthenticated();
    }
    
    protected boolean isAuthorized(String module, String action, boolean throwException) {
        if (isAuthenticated()) {
            if (CommonUtils.ADMINISTRATOR.equals(CommonUtils.getCurrentAccountUsername())) {
                return true;
            }
            if (roleService.isAuthorized(Objects.requireNonNull(CommonUtils.getCurrentAccountId()), module, action)) {
                return true;
            } else {
            	if (throwException) {
            		throw new ForbiddenException("You are not authorized to use this function!");
            	}               
            }
        }
        return false;
    }
}