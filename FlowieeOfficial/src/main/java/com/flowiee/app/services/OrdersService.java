package com.flowiee.app.services;

import com.flowiee.app.model.Orders;
import com.flowiee.app.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    public List<Orders> getAllOrders(){
        /*
        * Lấy ra danh sách toàn bộ đơn hàng
        * */
        return ordersRepository.findAll();
    }

    public List<Orders> getByStatus(String status){
        /*
        * Lấy ra danh sách đơn hàng theo trạng thái đơn được truyền vào (Chờ xác nhận, Đang giao,..)
        * */
        return ordersRepository.findByStatus(status);
    }
}
