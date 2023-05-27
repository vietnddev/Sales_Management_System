package com.flowiee.app.nguoidung.service;

import com.flowiee.app.nguoidung.entity.TaiKhoan;
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
public interface AccountService{

    List<TaiKhoan> getAll();

    TaiKhoan getAccountByUsername(String username);

    Optional<TaiKhoan> getAccountByID(int ID);

    int findIdByUsername(String username);

    TaiKhoan saveAccount(TaiKhoan accountEntity);

    void deleteAccountByID(int ID);

    String getUserName();

    String getUserNameByID(int id);

    String getIP();
}