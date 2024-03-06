package com.flowiee.sms.service;

import com.flowiee.sms.core.BaseService;
import com.flowiee.sms.model.dto.OrderDTO;
import com.flowiee.sms.entity.TicketExport;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TicketExportService extends BaseService<TicketExport> {
    List<TicketExport> findAll();

    Page<TicketExport> findAll(int pageSize, int pageNum);

    TicketExport save(OrderDTO orderDTO);
}