package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.sanpham.entity.DonHang;
import com.flowiee.app.sanpham.repository.DonHangRepository;
import com.flowiee.app.sanpham.services.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonHangServiceImpl implements DonHangService {
    @Autowired
    private DonHangRepository ordersRepository;

    @Override
    public List<DonHang> getAllOrders(){
        return ordersRepository.findAll();
    }

    @Override
    public List<DonHang> getByStatus(String status){
        return ordersRepository.findByTrangThai(status);
    }
}