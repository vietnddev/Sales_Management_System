package com.flowiee.pms.service.sales;

import com.flowiee.pms.service.BaseCurd;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.entity.sales.TicketExport;
import org.springframework.data.domain.Page;

public interface TicketExportService extends BaseCurd<TicketExport> {
    Page<TicketExport> findAll(int pageSize, int pageNum, Integer storageId);

    TicketExport save(OrderDTO orderDTO);
}