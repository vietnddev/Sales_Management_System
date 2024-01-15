package com.flowiee.app.service.impl;

import com.flowiee.app.entity.TicketExportGoods;
import com.flowiee.app.repository.TicketExportRepository;
import com.flowiee.app.service.OrderService;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.service.TicketExportGoodsService;
import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketExportGoodsServiceImpl implements TicketExportGoodsService {
    @Autowired
    private TicketExportRepository ticketExportRepository;
    @Autowired
    private OrderService           orderService;
    @Autowired
    private ProductService productService;

    @Override
    public List<TicketExportGoods> findAll() {
        return ticketExportRepository.findAll();
    }

    @Override
    public TicketExportGoods findById(Integer entityId) {
        return ticketExportRepository.findById(entityId).get();
    }

    @Override
    public TicketExportGoods save(TicketExportGoods ticket) {
        TicketExportGoods ticketExportSaved = ticketExportRepository.save(ticket);
        //Code for order status
        //PR -> Preparing
        //WS -> Waiting shipper
        //DE -> Delivering
        //DO -> Done
        //Update lại số lượng của sản phẩm còn trong kho
        orderService.findOrderDetailsByOrderId(ticket.getOrderId()).forEach(d -> {
            productService.updateProductVariantQuantity(productService.findProductVariantById(d.getProductVariant().getId()).getSoLuongKho() - d.getSoLuong(), d.getProductVariant().getId());
        });
        return ticketExportSaved;
    }

    @Override
    public TicketExportGoods update(TicketExportGoods ticket, Integer entityId) {
        ticket.setId(entityId);
        return ticketExportRepository.save(ticket);
    }

    @Override
    public String delete(Integer entityId) {
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}