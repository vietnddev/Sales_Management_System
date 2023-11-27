package com.flowiee.app.service.impl;

import com.flowiee.app.entity.OrderCart;
import com.flowiee.app.entity.SystemLog;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.repository.OrderCartRepository;
import com.flowiee.app.service.CartService;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private OrderCartRepository orderCartRepository;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private AccountService accountService;

    @Override
    public List<OrderCart> findAll() {
        return orderCartRepository.findAll();
    }

    @Override
    public List<OrderCart> findByAccountId(Integer accountId) {
        return orderCartRepository.findByAccountId(accountId);
    }

    @Override
    public OrderCart findById(int id) {
        return orderCartRepository.findById(id).orElse(null);
    }

    @Override
    public String save(OrderCart orderCart) {
        if (orderCart == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        orderCartRepository.save(orderCart);
        return "OK";
    }

    @Override
    public String delete(int id) {
        orderCartRepository.deleteById(id);
        SystemLog systemLog = new SystemLog();
        systemLog.setModule(SystemModule.SAN_PHAM.name());
        systemLog.setAction("DELETE_CART");
        systemLog.setCreatedBy(accountService.findCurrentAccountId());
        systemLog.setIp(accountService.findCurrentAccountIp());
        systemLog.setNoiDung("DELETE CART");
        systemLogService.writeLog(systemLog);
        return "OK";
    }
}
