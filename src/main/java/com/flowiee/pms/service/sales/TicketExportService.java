package com.flowiee.pms.service.sales;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.entity.sales.TicketExport;
import org.springframework.data.domain.Page;

public interface TicketExportService extends BaseService<TicketExport> {
    Page<TicketExport> findAll(int pageSize, int pageNum);

    TicketExport save(OrderDTO orderDTO);
}