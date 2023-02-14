package com.flowiee.app.services;

import com.flowiee.app.model.admin.Account;
import com.flowiee.app.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public List<Account> getAllAccount(){
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountByID(int ID){
        return Optional.of(accountRepository.getById(ID));
    }

    public void deleteAccountByID(int ID){
        Account account = accountRepository.findById(ID).orElse(null);
        if (account != null) {
            accountRepository.delete(account);
            System.out.println("Successfully deleted the account with id: " + ID);
        }
    }

    public void updateOrSave(){

    }
}
