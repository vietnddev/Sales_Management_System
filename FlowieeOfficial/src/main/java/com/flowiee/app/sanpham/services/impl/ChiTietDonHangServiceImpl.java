package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.sanpham.entity.DonHangChiTiet;
import com.flowiee.app.sanpham.repository.ChiTietDonHangRepository;
import com.flowiee.app.sanpham.services.ChiTietDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {
    @Autowired
    private ChiTietDonHangRepository detailOrderRepository;

    @Override
    public List<DonHangChiTiet> getDetailByOrdersID(int ordersID){
        return detailOrderRepository.findByordersID(ordersID);
    }
}