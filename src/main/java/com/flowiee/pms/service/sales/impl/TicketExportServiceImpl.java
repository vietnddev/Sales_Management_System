package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.entity.product.ProductHistory;
import com.flowiee.pms.entity.sales.TicketExport;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.sales.OrderRepository;
import com.flowiee.pms.repository.sales.TicketExportRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductHistoryService;
import com.flowiee.pms.service.product.ProductQuantityService;
import com.flowiee.pms.service.sales.TicketExportService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.constants.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketExportServiceImpl extends BaseService implements TicketExportService {
    private final TicketExportRepository ticketExportRepo;
    private final OrderRepository        orderRepository;
    private final ProductQuantityService productQuantityService;
    private final ProductHistoryService  productHistoryService;

    public TicketExportServiceImpl(TicketExportRepository ticketExportRepo, OrderRepository orderRepository, ProductQuantityService productQuantityService, ProductHistoryService productHistoryService) {
        this.ticketExportRepo = ticketExportRepo;
        this.orderRepository = orderRepository;
        this.productQuantityService = productQuantityService;
        this.productHistoryService = productHistoryService;
    }

    @Override
    public List<TicketExport> findAll() {
        return this.findAll(-1, -1, null).getContent();
    }

    @Override
    public Page<TicketExport> findAll(int pageSize, int pageNum, Integer storageId) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("exportTime").descending());
        }
        return ticketExportRepo.findAll(storageId, pageable);
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
        return ticketExportRepo.save(ticket);
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
        ticketExport.setExportTime(LocalDateTime.now());
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
            throw new BadRequestException(ErrorCode.ERROR_DATA_LOCKED.getDescription());
        }
        ticketExportToUpdate.setTitle(ticket.getTitle());
        ticketExportToUpdate.setNote(ticket.getNote());
        ticketExportToUpdate.setStatus(ticket.getStatus());
        TicketExport ticketExportUpdated = ticketExportRepo.save(ticketExportToUpdate);

        if (TicketExportStatus.COMPLETED.name().equals(ticketExportUpdated.getStatus())) {
            for (ProductVariantTemp productVariantTemp : ticketExportUpdated.getListProductVariantTemp()) {
                int soldQtyInOrder = productVariantTemp.getQuantity();
                productQuantityService.updateProductVariantQuantityDecrease(soldQtyInOrder, productVariantTemp.getProductVariant().getId());
                //Save log
                int storageQty = productVariantTemp.getProductVariant().getStorageQty();
                int soldQty = productVariantTemp.getProductVariant().getSoldQty();
                ProductHistory productHistory = new ProductHistory();
                productHistory.setProduct(productVariantTemp.getProductVariant().getProduct());
                productHistory.setProductDetail(productVariantTemp.getProductVariant());
                productHistory.setTitle("Cập nhật số lượng cho [" + productVariantTemp.getProductVariant().getVariantName() + "] - " + ticket.getTitle());
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
        Optional<TicketExport> ticketExportToDelete = this.findById(ticketExportId);
        if (ticketExportToDelete.isEmpty()) {
            throw new ResourceNotFoundException("Ticket export not found!");
        }
        if ("COMPLETED".equals(ticketExportToDelete.get().getStatus())) {
            throw new BadRequestException(ErrorCode.ERROR_DATA_LOCKED.getDescription());
        }

        Order order = orderRepository.findByTicketExport(ticketExportId);
        if (order != null) {
            order.setTicketExport(null);
            orderRepository.save(order);
        }
        ticketExportRepo.deleteById(ticketExportId);

        systemLogService.writeLogDelete(MODULE.STORAGE, ACTION.STG_TICKET_EX, MasterObject.TicketExport, "Xóa phiếu xuất hàng", ticketExportToDelete.get().getTitle());

        return MessageCode.DELETE_SUCCESS.getDescription();
    }
}