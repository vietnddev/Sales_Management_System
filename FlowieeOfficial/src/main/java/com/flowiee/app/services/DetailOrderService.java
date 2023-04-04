package com.flowiee.app.services;

import com.flowiee.app.model.DetailOrder;
import com.flowiee.app.repositories.DetailOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailOrderService {
    @Autowired
    private DetailOrderRepository detailOrderRepository;

    public List<DetailOrder> getDetailByOrdersID(int ordersID){
        return detailOrderRepository.findByordersID(ordersID);
    }
}
