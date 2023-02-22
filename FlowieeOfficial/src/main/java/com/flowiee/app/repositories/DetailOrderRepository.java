package com.flowiee.app.repositories;

import com.flowiee.app.model.sales.DetailOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailOrderRepository extends JpaRepository<DetailOrder, Integer> {
    public List<DetailOrder> findByordersID(int ordersID);
}
