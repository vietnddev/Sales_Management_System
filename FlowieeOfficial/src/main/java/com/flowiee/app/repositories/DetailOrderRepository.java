package com.flowiee.app.repositories;

import com.flowiee.app.model.DetailOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder, Integer> {
    public List<DetailOrder> findByordersID(int ordersID);
}
