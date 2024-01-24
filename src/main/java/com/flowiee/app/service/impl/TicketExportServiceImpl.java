package com.flowiee.app.service.impl;

import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.entity.TicketExport;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.repository.OrderRepository;
import com.flowiee.app.repository.TicketExportRepository;
import com.flowiee.app.service.OrderService;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.service.TicketExportService;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class TicketExportServiceImpl implements TicketExportService {
    @Autowired
    private TicketExportRepository ticketExportRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;

    @Override
    public List<TicketExport> findAll() {
        return ticketExportRepository.findAll();
    }

    @Override
    public TicketExport findById(Integer entityId) {
        return ticketExportRepository.findById(entityId).orElse(null);
    }

    @Override
    public TicketExport save(TicketExport ticket) {
        //TicketExport ticketExportSaved = ticketExportRepository.save(ticket);
        //Code for order status
        //PR -> Preparing
        //WS -> Waiting shipper
        //DE -> Delivering
        //DO -> Done
        //Update lại số lượng của sản phẩm còn trong kho
//        orderService.findOrderDetailsByOrderId(ticket.getOrderId()).forEach(d -> {
//            productService.updateProductVariantQuantity(productService.findProductVariantById(d.getProductVariant().getId()).getSoLuongKho() - d.getSoLuong(), d.getProductVariant().getId());
//        });
        return null;
    }

    @Override
    public TicketExport save(OrderDTO orderDTO) {
        if (!ObjectUtils.isNotEmpty(orderDTO)) {
            throw new BadRequestException();
        }
        TicketExport ticketExport = new TicketExport();
        ticketExport.setTitle("Xuất hàng cho đơn " + orderDTO.getOrderCode());
        ticketExport.setExporter(CommonUtils.getCurrentAccountUsername());
        ticketExport.setExportTime(new Date());
        ticketExport.setNote(null);
        ticketExport.setStatus("DRAFT");
        ticketExport = ticketExportRepository.save(ticketExport);
        //orderRepository.setTicketExportInfo(orderDTO.getOrderId(), ticketExport.getId());
        return ticketExport;
    }

    @Override
    public TicketExport update(TicketExport ticket, Integer entityId) {
        ticket.setId(entityId);
        return ticketExportRepository.save(ticket);
    }

    @Override
    public String delete(Integer entityId) {
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}