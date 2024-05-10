package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.entity.sales.OrderDetail;
import com.flowiee.pms.entity.product.ProductHistory;
import com.flowiee.pms.entity.sales.TicketExport;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.sales.OrderRepository;
import com.flowiee.pms.repository.sales.TicketExportRepository;
import com.flowiee.pms.service.product.ProductHistoryService;
import com.flowiee.pms.service.product.ProductQuantityService;
import com.flowiee.pms.service.sales.TicketExportService;
import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.MessageUtils;
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
import java.util.Optional;

@Service
public class TicketExportServiceImpl implements TicketExportService {
    @Autowired
    private TicketExportRepository ticketExportRepo;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductQuantityService productQuantityService;
    @Autowired
    private ProductHistoryService productHistoryService;

    @Override
    public List<TicketExport> findAll() {
        return this.findAll(-1, -1).getContent();
    }

    @Override
    public Page<TicketExport> findAll(int pageSize, int pageNum) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("exportTime").descending());
        }
        return ticketExportRepo.findAll(pageable);
    }

    @Override
    public Optional<TicketExport> findById(Integer entityId) {
        return ticketExportRepo.findById(entityId);
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
        if (orderRepository.findById(orderDTO.getId()).isEmpty()) {
            throw new BadRequestException();
        }
        TicketExport ticketExport = new TicketExport();
        ticketExport.setTitle("Xuất hàng cho đơn " + orderDTO.getCode());
        ticketExport.setExporter(CommonUtils.getUserPrincipal().getUsername());
        ticketExport.setExportTime(new Date());
        ticketExport.setNote(null);
        ticketExport.setStatus("DRAFT");
        TicketExport ticketExportSaved = ticketExportRepo.save(ticketExport);
        orderRepository.setTicketExportInfo(orderDTO.getId(), ticketExportSaved.getId());
        return ticketExportSaved;
    }

    @Transactional
    @Override
    public TicketExport update(TicketExport ticket, Integer ticketExportId) {
        ticket.setId(ticketExportId);
        Optional<TicketExport> ticketExportOptional = this.findById(ticketExportId);
        if (ticketExportOptional.isEmpty()) {
            throw new BadRequestException();
        }
        TicketExport ticketExportToUpdate = ticketExportOptional.get();
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
                int productVariantId = orderDetail.getProductDetail().getId();
                productQuantityService.updateProductVariantQuantityDecrease(soldQtyInOrder, productVariantId);

                //Save log
                int storageQty = orderDetail.getProductDetail().getStorageQty();
                int soldQty = orderDetail.getProductDetail().getSoldQty();
                ProductHistory productHistory = new ProductHistory();
                productHistory.setProduct(orderDetail.getProductDetail().getProduct());
                productHistory.setProductDetail(orderDetail.getProductDetail());
                productHistory.setTitle("Cập nhật số lượng cho [" + orderDetail.getProductDetail().getVariantName() + "] - " + ticket.getTitle());
                productHistory.setField("Storage Qty | Sold Qty");
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