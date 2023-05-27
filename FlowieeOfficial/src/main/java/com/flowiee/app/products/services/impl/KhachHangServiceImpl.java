package com.flowiee.app.products.services.impl;

import com.flowiee.app.products.entity.KhachHang;
import com.flowiee.app.products.repository.KhachHangRepository;
import com.flowiee.app.products.services.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhachHangServiceImpl implements KhachHangService {
    @Autowired
    private KhachHangRepository customerRepository;

    @Override
    public List<KhachHang> getAll() {
        return customerRepository.findAll();
    }
}