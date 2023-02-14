package com.flowiee.app.services;

import com.flowiee.app.model.admin.Account;
import com.flowiee.app.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public List<Account> getAllAccount(){
        return accountRepository.findAll();
    }
}
