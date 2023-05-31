package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.hethong.entity.SystemLog;
import com.flowiee.app.hethong.model.action.SanPhamAction;
import com.flowiee.app.hethong.model.module.SystemModule;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.service.SystemLogService;
import com.flowiee.app.sanpham.entity.SanPham;
import com.flowiee.app.sanpham.repository.SanPhamRepository;
import com.flowiee.app.sanpham.services.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepository productsRepository;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private AccountService accountService;

    @Override
    public List<SanPham> getAllProducts() {
        return productsRepository.findAll();
    }

    @Override
    public SanPham findById(int productID) {
        return productsRepository.findById(productID).orElse(null);
    }

    @Override
    public void insertProduct(SanPham sanPham) {
        productsRepository.save(sanPham);
    }

    @Override
    public void update(SanPham sanPham, int id) {
        SanPham sanPhamBefore = productsRepository.findById(id).get();
        sanPham.setId(id);
        productsRepository.save(sanPham);
        //Ghi log
        SystemLog systemLog = SystemLog.builder()
            .module(SystemModule.SAN_PHAM.name())
            .action(SanPhamAction.UPDATE_SANPHAM.name())
            .noiDung(sanPhamBefore.toString())
            .noiDungCapNhat(sanPham.toString())
            .ip("")
            .account(Account.builder().id(accountService.findIdByUsername(accountService.getUserName())).build())
            .build();
        systemLogService.writeLog(systemLog);
    }

    @Override
    public void deleteProduct(int productID) {
        productsRepository.deleteById(productID);
    }
}