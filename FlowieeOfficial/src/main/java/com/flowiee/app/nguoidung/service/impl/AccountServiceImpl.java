package com.flowiee.app.nguoidung.service.impl;

import com.flowiee.app.nguoidung.entity.TaiKhoan;
import com.flowiee.app.nguoidung.repository.AccountRepository;
import com.flowiee.app.nguoidung.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<TaiKhoan> getAll(){
        return accountRepository.findAll();
    }

    @Override
    public TaiKhoan getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Optional<TaiKhoan> getAccountByID(int ID) {
        return accountRepository.findById(ID);
    }

    @Override
    public int findIdByUsername(String username) {
        return accountRepository.findIdByUsername(username);
    }

    @Override
    public TaiKhoan saveAccount(TaiKhoan accountEntity) {
        return accountRepository.save(accountEntity);
    }

    @Override
    public void deleteAccountByID(int ID) {
        TaiKhoan accountEntity = accountRepository.findById(ID).orElse(null);
        if (accountEntity != null) {
            accountRepository.delete(accountEntity);
            System.out.println("Successfully deleted the accountEntity with id: " + ID);
        }
    }

    @Override
    public String getUserName() {
        String username = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username.toLowerCase();
    }

    @Override
    public String getUserNameByID(int id) {
        return accountRepository.findUsernameById(id);
    }

    @Override
    public String getIP() {
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