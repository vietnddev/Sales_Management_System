package com.flowiee.app.nguoidung.service;

import com.flowiee.app.nguoidung.entity.AccountEntity;
import com.flowiee.app.nguoidung.repository.AccountRepository;
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

    public List<AccountEntity> getAll(){
        return accountRepository.findAll();
    }

    public AccountEntity getAccountByUsername(String username){
        return accountRepository.findByUsername(username);
    }

    public Optional<AccountEntity> getAccountByID(int ID){
        return accountRepository.findById(ID);
    }

    public int findIdByUsername(String username){
        return accountRepository.findIdByUsername(username);
    }

    public AccountEntity saveAccount(AccountEntity accountEntity){
        return accountRepository.save(accountEntity);
    }

    public void deleteAccountByID(int ID){
        AccountEntity accountEntity = accountRepository.findById(ID).orElse(null);
        if (accountEntity != null) {
            accountRepository.delete(accountEntity);
            System.out.println("Successfully deleted the accountEntity with id: " + ID);
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
        return accountRepository.findUsernameById(id);
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
