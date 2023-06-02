package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.exception.NotFoundException;
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
    public List<SanPham> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public SanPham findById(int productID) {
        return productsRepository.findById(productID).orElse(null);
    }

    @Override
    public String save(SanPham sanPham) {
        if (sanPham.getLoaiSanPham() == null ||
            sanPham.getTenSanPham() == null) {
            throw new BadRequestException();
        }
        try {
            productsRepository.save(sanPham);
            SystemLog systemLog = new SystemLog();
            systemLog.setModule(SystemModule.SAN_PHAM.name());
            systemLog.setAction(SanPhamAction.DELETE_SANPHAM.name());
            systemLog.setNoiDung(sanPham.toString());
            systemLog.setAccount(accountService.getCurrentAccount());
            systemLog.setIp(accountService.getIP());
            systemLogService.writeLog(systemLog);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String update(SanPham sanPham, int id) {
        if (id <= 0 || this.findById(id) == null) {
            throw new NotFoundException();
        }
        SanPham sanPhamBefore = this.findById(id);
        try {
            sanPham.setId(id);
            productsRepository.save(sanPham);
            SystemLog systemLog = new SystemLog();
            systemLog.setModule(SystemModule.SAN_PHAM.name());
            systemLog.setAction(SanPhamAction.UPDATE_SANPHAM.name());
            systemLog.setNoiDung(sanPhamBefore.toString());
            systemLog.setNoiDungCapNhat(sanPham.toString());
            systemLog.setAccount(accountService.getCurrentAccount());
            systemLog.setIp(accountService.getIP());
            systemLogService.writeLog(systemLog);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    @Override
    public String delete(int id) {
        if (id <= 0) {
            throw new NotFoundException();
        }
        SanPham sanPhamToDelete = this.findById(id);
        if (sanPhamToDelete == null) {
            throw new NotFoundException();
        }
        try {
            productsRepository.deleteById(id);
            SystemLog systemLog = new SystemLog();
            systemLog.setModule(SystemModule.SAN_PHAM.name());
            systemLog.setAction(SanPhamAction.DELETE_SANPHAM.name());
            systemLog.setNoiDung(sanPhamToDelete.toString());
            systemLog.setAccount(accountService.getCurrentAccount());
            systemLog.setIp(accountService.getIP());
            systemLogService.writeLog(systemLog);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }
}