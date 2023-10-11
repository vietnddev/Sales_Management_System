package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.hethong.entity.SystemLog;
import com.flowiee.app.common.module.SystemModule;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.sanpham.entity.Cart;
import com.flowiee.app.sanpham.repository.CartRepository;
import com.flowiee.app.sanpham.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private AccountService accountService;

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public List<Cart> findByAccountId(int accountId) {
        return cartRepository.findByAccountId(accountId + "");
    }

    @Override
    public Cart findById(int id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public String save(Cart cart) {
        cartRepository.save(cart);
        return "OK";
    }

    @Override
    public String delete(int id) {
        cartRepository.deleteById(id);
        SystemLog systemLog = new SystemLog();
        systemLog.setModule(SystemModule.SAN_PHAM.name());
        systemLog.setAction("DELETE_CART");
        systemLog.setAccount(FlowieeUtil.ACCOUNT);
        systemLog.setIp(FlowieeUtil.ACCOUNT_IP);
        systemLog.setNoiDung("DELETE CART");
        systemLogService.writeLog(systemLog);
        return "OK";
    }
}
