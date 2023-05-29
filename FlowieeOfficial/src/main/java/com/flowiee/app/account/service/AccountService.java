package com.flowiee.app.account.service;

import com.flowiee.app.account.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccountService{

    List<Account> getAll();

    Account getAccountByUsername(String username);

    Optional<Account> getAccountByID(int ID);

    int findIdByUsername(String username);

    Account saveAccount(Account accountEntity);

    void deleteAccountByID(int ID);

    String getUserName();

    String getUserNameByID(int id);

    String getIP();
}