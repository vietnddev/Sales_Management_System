package com.flowiee.app.products.services;

import com.flowiee.app.products.entity.KhachHang;
import com.flowiee.app.products.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhachHangService {
    @Autowired
    private KhachHangRepository customerRepository;

    public List<KhachHang> getAll() {
        return customerRepository.findAll();
    }
}
