package com.flowiee.app.controller.rest;

import com.flowiee.app.entity.Account;
import com.flowiee.app.exception.ApiException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/account")
@Tag(name = "Account system API", description = "Quản lý tài khoản hệ thống")
public class AccountRestController {
    @Autowired
    private AccountService accountService;

    @Operation(summary = "Find all accounts")
    @GetMapping("/all")
    public ApiResponse<List<Account>> findAllAccounts() {
        try {
            return ApiResponse.ok(accountService.findAll());
        } catch (RuntimeException ex) {
            throw new ApiException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "account"));
        }
    }
}