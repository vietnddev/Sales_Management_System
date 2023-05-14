package com.flowiee.app.products.services;

import com.flowiee.app.products.entity.DonHangChiTiet;
import com.flowiee.app.products.repository.ChiTietDonHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiTietDonHangService {
    @Autowired
    private ChiTietDonHangRepository detailOrderRepository;

    public List<DonHangChiTiet> getDetailByOrdersID(int ordersID){
        return detailOrderRepository.findByordersID(ordersID);
    }
}
