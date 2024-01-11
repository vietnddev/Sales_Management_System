package com.flowiee.app.repository;

import com.flowiee.app.entity.TicketExportGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketExportRepository extends JpaRepository<TicketExportGoods, Integer> {
}