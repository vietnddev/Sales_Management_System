package com.flowiee.app.repository.product;

import com.flowiee.app.entity.TicketExportGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketExportGoodsRepository extends JpaRepository<TicketExportGoods, Integer> {
}