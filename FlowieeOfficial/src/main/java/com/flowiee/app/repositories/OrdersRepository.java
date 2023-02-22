package com.flowiee.app.repositories;

import com.flowiee.app.model.sales.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    public List<Orders> findByStatus(String status);
}
