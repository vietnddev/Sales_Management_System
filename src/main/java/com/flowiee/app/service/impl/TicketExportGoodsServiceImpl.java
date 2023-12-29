package com.flowiee.app.service.impl;

import com.flowiee.app.entity.TicketExportGoods;
import com.flowiee.app.repository.TicketExportGoodsRepository;
import com.flowiee.app.service.OrderDetailService;
import com.flowiee.app.service.OrderService;
import com.flowiee.app.service.ProductVariantService;
import com.flowiee.app.service.TicketExportGoodsService;
import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketExportGoodsServiceImpl implements TicketExportGoodsService {
    @Autowired
    private TicketExportGoodsRepository ticketExportGoodsRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ProductVariantService productVariantService;

    @Override
    public List<TicketExportGoods> findAll() {
        return ticketExportGoodsRepository.findAll();
    }

    @Override
    public TicketExportGoods findById(Integer entityId) {
        return ticketExportGoodsRepository.findById(entityId).get();
    }

    @Override
    public String save(TicketExportGoods ticket) {
        ticketExportGoodsRepository.save(ticket);
        //Code for order status
        //PR -> Preparing
        //WS -> Waiting shipper
        //DE -> Delivering
        //DO -> Done
        //Update lại số lượng của sản phẩm còn trong kho
        orderDetailService.findByDonHangId(ticket.getOrderId()).forEach(d -> {
            productVariantService.updateSoLuong(productVariantService.findById(d.getProductVariant().getId()).getSoLuongKho() - d.getSoLuong(), d.getProductVariant().getId());
        });
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(TicketExportGoods ticket, Integer entityId) {
        ticket.setId(entityId);
        ticketExportGoodsRepository.save(ticket);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}