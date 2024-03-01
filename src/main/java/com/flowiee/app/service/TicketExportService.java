package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.model.dto.OrderDTO;
import com.flowiee.app.entity.TicketExport;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TicketExportService extends BaseService<TicketExport> {
    List<TicketExport> findAll();

    Page<TicketExport> findAll(int pageSize, int pageNum);

    TicketExport save(OrderDTO orderDTO);
}