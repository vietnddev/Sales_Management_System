package com.flowiee.app.repositories;

import com.flowiee.app.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    public List<Orders> findByStatus(String status);
}
