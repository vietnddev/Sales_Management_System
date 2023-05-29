package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.sanpham.entity.KhachHang;
import com.flowiee.app.sanpham.repository.KhachHangRepository;
import com.flowiee.app.sanpham.services.KhachHangService;
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