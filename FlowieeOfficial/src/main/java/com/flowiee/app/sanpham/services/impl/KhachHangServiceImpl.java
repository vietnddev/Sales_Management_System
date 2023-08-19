package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.sanpham.entity.KhachHang;
import com.flowiee.app.sanpham.repository.KhachHangRepository;
import com.flowiee.app.sanpham.services.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhachHangServiceImpl implements KhachHangService {
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private AccountService accountService;

    @Override
    public List<KhachHang> findAll() {
        return khachHangRepository.findAll();
    }

    @Override
    public KhachHang findById(int id) {
        return khachHangRepository.findById(id).orElse(null);
    }

    @Override
    public String save(KhachHang khachHang) {
        if (khachHang == null) {
            return "NOK";
        }
        khachHang.setCreatedBy(accountService.getCurrentAccount().getId() + "");
        khachHangRepository.save(khachHang);
        return "OK";
    }

    @Override
    public String update(KhachHang khachHang, int id) {
        if (id <=0 || this.findById(id) == null) {
            return "NOK";
        }
        khachHang.setId(id);
        khachHangRepository.save(khachHang);
        return "OK";
    }

    @Override
    public String delete(int id) {
        if (id <=0 || this.findById(id) == null) {
            return "NOK";
        }
        khachHangRepository.deleteById(id);
        return "OK";
    }
}