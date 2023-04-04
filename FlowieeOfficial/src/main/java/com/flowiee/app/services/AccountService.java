package com.flowiee.app.services;

import com.flowiee.app.model.Account;
import com.flowiee.app.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService{
    @Autowired
    AccountRepository accountRepository;

    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Account getAccountByUsername(String username){
        return accountRepository.findByUsername(username);
    }

    public Optional<Account> getAccountByID(int ID){
        return accountRepository.findById(ID);
    }

    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }

    public void deleteAccountByID(int ID){
        Account account = accountRepository.findById(ID).orElse(null);
        if (account != null) {
            accountRepository.delete(account);
            System.out.println("Successfully deleted the account with id: " + ID);
        }
    }

    public String getUserName(){
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username.toLowerCase();
    }

    public String getUserNameByID(int id){
        return accountRepository.findUsernameByID(id);
    }

    public String getIP(){
        WebAuthenticationDetails details = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object authDetails = authentication.getDetails();
            if (authDetails instanceof WebAuthenticationDetails) {
                details = (WebAuthenticationDetails) authDetails;
            }
        }
        if (details != null){
            return details.getRemoteAddress();
        }
        return "unknown";
    }
}
