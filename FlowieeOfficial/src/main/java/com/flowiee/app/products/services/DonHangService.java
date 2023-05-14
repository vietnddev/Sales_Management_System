package com.flowiee.app.products.services;

import com.flowiee.app.products.entity.DonHang;
import com.flowiee.app.products.repository.DonHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonHangService {
    @Autowired
    private DonHangRepository ordersRepository;

    public List<DonHang> getAllOrders(){
        /*
        * Lấy ra danh sách toàn bộ đơn hàng
        * */
        return ordersRepository.findAll();
    }

    public List<DonHang> getByStatus(String status){
        /*
        * Lấy ra danh sách đơn hàng theo trạng thái đơn được truyền vào (Chờ xác nhận, Đang giao,..)
        * */
        return ordersRepository.findByTrangThai(status);
    }
}
