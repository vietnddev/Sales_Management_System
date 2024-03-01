package com.flowiee.app.service.impl;

import com.flowiee.app.model.dto.OrderDTO;
import com.flowiee.app.entity.Order;
import com.flowiee.app.entity.OrderDetail;
import com.flowiee.app.entity.ProductHistory;
import com.flowiee.app.entity.TicketExport;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.repository.OrderRepository;
import com.flowiee.app.repository.TicketExportRepository;
import com.flowiee.app.service.ProductHistoryService;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.service.TicketExportService;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.utils.MessageUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TicketExportServiceImpl implements TicketExportService {
    @Autowired private TicketExportRepository ticketExportRepo;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductService productService;
    @Autowired private ProductHistoryService productHistoryService;

    @Override
    public List<TicketExport> findAll() {
        return ticketExportRepo.findAll();
    }

    @Override
    public Page<TicketExport> findAll(int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("exportTime").descending());
        return ticketExportRepo.findAll(pageable);
    }

    @Override
    public TicketExport findById(Integer entityId) {
        return ticketExportRepo.findById(entityId).orElse(null);
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

    @Transactional
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
        ticketExport = ticketExportRepo.save(ticketExport);
        orderRepository.setTicketExportInfo(orderDTO.getOrderId(), ticketExport.getId());
        return ticketExport;
    }

    @Transactional
    @Override
    public TicketExport update(TicketExport ticket, Integer ticketExportId) {
        ticket.setId(ticketExportId);
        TicketExport ticketExportToUpdate = this.findById(ticketExportId);
        if (ObjectUtils.isEmpty(ticketExportToUpdate)) {
            throw new BadRequestException();
        }
        if ("COMPLETED".equals(ticketExportToUpdate.getStatus()) || "CANCEL".equals(ticketExportToUpdate.getStatus())) {
            throw new BadRequestException(MessageUtils.ERROR_DATA_LOCKED);
        }
        ticketExportToUpdate.setTitle(ticket.getTitle());
        ticketExportToUpdate.setNote(ticket.getNote());
        ticketExportToUpdate.setStatus(ticket.getStatus());
        TicketExport ticketExportUpdated = ticketExportRepo.save(ticketExportToUpdate);

        if (AppConstants.TICKET_EX_STATUS.COMPLETED.name().equals(ticketExportUpdated.getStatus())) {
            Order order = orderRepository.findByTicketExport(ticketExportUpdated.getId());
            for (OrderDetail orderDetail : order.getListOrderDetail()) {
                int soldQtyInOrder = orderDetail.getQuantity();
                int productVariantId = orderDetail.getProductVariant().getId();
                productService.updateProductVariantQuantity(soldQtyInOrder, productVariantId, "D");

                //Save log
                int storageQty = orderDetail.getProductVariant().getStorageQty();
                int soldQty = orderDetail.getProductVariant().getSoldQty();
                ProductHistory productHistory = new ProductHistory();
                productHistory.setProduct(orderDetail.getProductVariant().getProduct());
                productHistory.setProductVariant(orderDetail.getProductVariant());
                productHistory.setTitle("Cập nhật số lượng cho [" + orderDetail.getProductVariant().getVariantName() + "] - " + ticket.getTitle());
                productHistory.setFieldName("Storage Qty | Sold Qty");
                productHistory.setOldValue(storageQty + " | " + soldQty);
                productHistory.setNewValue((storageQty - soldQtyInOrder) +  " | " + (soldQty + soldQtyInOrder));
                productHistoryService.save(productHistory);
            }
        }
        return ticketExportUpdated;
    }

    @Override
    public String delete(Integer ticketExportId) {
        Order order = orderRepository.findByTicketExport(ticketExportId);
        if (order != null) {
            order.setTicketExport(null);
            orderRepository.save(order);
        }
        ticketExportRepo.deleteById(ticketExportId);
        return MessageUtils.DELETE_SUCCESS;
    }
}