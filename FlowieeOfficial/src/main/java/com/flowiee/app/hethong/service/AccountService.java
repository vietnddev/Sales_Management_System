package com.flowiee.app.hethong.service;

import com.flowiee.app.hethong.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccountService{

    List<Account> findAll();

    Account findByUsername(String username);

    Account findById(int ID);

    int findIdByUsername(String username);

    Account save(Account account);

    Account update(Account account);

    void delete(int ID);

    boolean isLogin();
}