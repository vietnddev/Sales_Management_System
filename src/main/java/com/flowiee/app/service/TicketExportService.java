package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.entity.TicketExport;

import java.util.List;

public interface TicketExportService extends BaseService<TicketExport> {
    List<TicketExport> findAll();

    TicketExport save(OrderDTO orderDTO);
}