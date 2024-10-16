package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.product.ProductVariantTemp;
import com.flowiee.pms.entity.sales.OrderDetail;
import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.entity.product.ProductHistory;
import com.flowiee.pms.entity.sales.TicketExport;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.product.ProductDetailTempRepository;
import com.flowiee.pms.repository.sales.OrderRepository;
import com.flowiee.pms.repository.sales.TicketExportRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductHistoryService;
import com.flowiee.pms.service.product.ProductQuantityService;
import com.flowiee.pms.service.sales.OrderItemsService;
import com.flowiee.pms.service.sales.TicketExportService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.constants.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TicketExportServiceImpl extends BaseService implements TicketExportService {
    OrderRepository        mvOrderRepository;
    OrderItemsService      mvOrderItemsService;
    TicketExportRepository mvTicketExportRepository;
    ProductHistoryService       mvProductHistoryService;
    ProductQuantityService      mvProductQuantityService;
    ProductDetailTempRepository mvProductVariantTempRepository;

    @Override
    public List<TicketExport> findAll() {
        return this.findAll(-1, -1, null).getContent();
    }

    @Override
    public Page<TicketExport> findAll(int pageSize, int pageNum, Long storageId) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("exportTime").descending());
        }
        Page<TicketExport> ticketExportPage = mvTicketExportRepository.findAll(storageId, pageable);
        for (TicketExport ticketExport : ticketExportPage.getContent()) {
            BigDecimal[] totalValueAndItems = getTotalValueAndItems(ticketExport.getListProductVariantTemp());
            ticketExport.setTotalValue(totalValueAndItems[0]);
            ticketExport.setTotalItems(totalValueAndItems[1].intValue());
            ticketExport.setStorageName(ticketExport.getStorage().getName());
            ticketExport.setNote(ticketExport.getNote() != null ? ticketExport.getNote() : "");
        }
        return ticketExportPage;
    }

    @Override
    public Optional<TicketExport> findById(Long entityId) {
        Optional<TicketExport> ticketExportOpt = mvTicketExportRepository.findById(entityId);
        if (ticketExportOpt.isEmpty()) {
            return Optional.empty();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        BigDecimal[] totalValueAndItems = getTotalValueAndItems(ticketExportOpt.get().getListProductVariantTemp());
        ticketExportOpt.get().setTotalValue(totalValueAndItems[0]);
        ticketExportOpt.get().setTotalItems(totalValueAndItems[1].intValue());
        ticketExportOpt.get().setExportTimeStr(ticketExportOpt.get().getExportTime().format(formatter));
        return ticketExportOpt;
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
        return mvTicketExportRepository.save(ticket);
    }

    @Transactional
    @Override
    public TicketExport save(OrderDTO orderDTO) {
        if (!ObjectUtils.isNotEmpty(orderDTO) || mvOrderRepository.findById(orderDTO.getId()).isEmpty()) {
            throw new BadRequestException("Đơn hàng không tồn tại");
        }
        TicketExport ticketExportSaved = this.save(TicketExport.builder()
                .title("Xuất hàng cho đơn " + orderDTO.getCode())
                .exporter(CommonUtils.getUserPrincipal().getUsername())
                .exportTime(LocalDateTime.now())
                .status(TicketExportStatus.DRAFT.name())
                .build());
        mvOrderRepository.updateTicketExportInfo(orderDTO.getId(), ticketExportSaved.getId());
        return ticketExportSaved;
    }

    @Transactional
    @Override
    public TicketExport createDraftTicketExport(long storageId, String title, String orderCode) {
        Order order = null;
        if (ObjectUtils.isNotEmpty(orderCode)) {
            order = mvOrderRepository.findByOrderCode(orderCode);
            if (order == null) {
                throw new BadRequestException("Mã đơn hàng không tồn tại!");
            } else {
                if (order.getTicketExport() != null) {
                    throw new BadRequestException("Đơn hàng này đã được tạo phiếu xuất kho!");
                }
            }
        }
        TicketExport ticketExportSaved = this.save(TicketExport.builder()
                .title(title)
                .status(TicketExportStatus.DRAFT.name())
                .exporter(CommonUtils.getUserPrincipal().getUsername())
                .exportTime(LocalDateTime.now())
                .storage(new Storage(storageId))
                .note(order != null ? "Phiếu xuất hàng cho đơn " + order.getCode() : "")
                .build());
        if (order != null) {
            mvOrderRepository.updateTicketExportInfo(order.getId(), ticketExportSaved.getId());
            List<OrderDetail> orderDetails = mvOrderItemsService.findByOrderId(order.getId());
            for (OrderDetail item : orderDetails) {
                mvProductVariantTempRepository.save(ProductVariantTemp.builder()
                        .ticketExport(ticketExportSaved)
                        .productVariant(item.getProductDetail())
                        .sellPrice(item.getPrice())
                        .quantity(item.getQuantity())
                        .note(item.getNote())
                        .build());
            }
        }
        return ticketExportSaved;
    }

    @Transactional
    @Override
    public TicketExport update(TicketExport ticket, Long ticketExportId) {
        ticket.setId(ticketExportId);
        Optional<TicketExport> ticketExportOptional = this.findById(ticketExportId);
        if (ticketExportOptional.isEmpty()) {
            throw new BadRequestException();
        }
        TicketExport ticketExportToUpdate = ticketExportOptional.get();
        if (TicketExportStatus.COMPLETED.name().equals(ticketExportToUpdate.getStatus()) || TicketExportStatus.CANCEL.name().equals(ticketExportToUpdate.getStatus())) {
            throw new BadRequestException(ErrorCode.ERROR_DATA_LOCKED.getDescription());
        }
        ticketExportToUpdate.setTitle(ticket.getTitle());
        ticketExportToUpdate.setNote(ticket.getNote());
        ticketExportToUpdate.setStatus(ticket.getStatus());
        TicketExport ticketExportUpdated = mvTicketExportRepository.save(ticketExportToUpdate);

        if (TicketExportStatus.COMPLETED.name().equals(ticketExportUpdated.getStatus())) {
            for (ProductVariantTemp productVariantTemp : ticketExportUpdated.getListProductVariantTemp()) {
                int soldQtyInOrder = productVariantTemp.getQuantity();
                mvProductQuantityService.updateProductVariantQuantityDecrease(soldQtyInOrder, productVariantTemp.getProductVariant().getId());
                //Save log
                int storageQty = productVariantTemp.getProductVariant().getStorageQty();
                int soldQty = productVariantTemp.getProductVariant().getSoldQty();
                ProductHistory productHistory = ProductHistory.builder()
                    .product(productVariantTemp.getProductVariant().getProduct())
                    .productDetail(productVariantTemp.getProductVariant())
                    .title("Cập nhật số lượng cho [" + productVariantTemp.getProductVariant().getVariantName() + "] - " + ticket.getTitle())
                    .field("Storage Qty | Sold Qty")
                    .oldValue(storageQty + " | " + soldQty)
                    .newValue((storageQty - soldQtyInOrder) +  " | " + (soldQty + soldQtyInOrder))
                    .build();
                mvProductHistoryService.save(productHistory);
            }
        }
        return ticketExportUpdated;
    }

    @Override
    public String delete(Long ticketExportId) {
        Optional<TicketExport> ticketExportToDelete = this.findById(ticketExportId);
        if (ticketExportToDelete.isEmpty()) {
            throw new ResourceNotFoundException("Ticket export not found!");
        }
        if (TicketExportStatus.COMPLETED.name().equals(ticketExportToDelete.get().getStatus())) {
            throw new BadRequestException(ErrorCode.ERROR_DATA_LOCKED.getDescription());
        }

        Order order = mvOrderRepository.findByTicketExport(ticketExportId);
        if (order != null) {
            order.setTicketExport(null);
            mvOrderRepository.save(order);
        }
        mvTicketExportRepository.deleteById(ticketExportId);

        systemLogService.writeLogDelete(MODULE.STORAGE, ACTION.STG_TICKET_EX, MasterObject.TicketExport, "Xóa phiếu xuất hàng", ticketExportToDelete.get().getTitle());

        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    private BigDecimal[] getTotalValueAndItems(List<ProductVariantTemp> pProductVariantTempList) {
        BigDecimal totalValue = BigDecimal.ZERO;
        int totalItems = 0;
        if (pProductVariantTempList != null) {
            for (ProductVariantTemp p : pProductVariantTempList) {
                if (p.getTicketImport() != null && p.getPurchasePrice() != null) {
                    totalValue = totalValue.add(p.getPurchasePrice().multiply(new BigDecimal(p.getQuantity())));
                }
                if (p.getTicketExport() != null && p.getSellPrice() != null) {
                    totalValue = totalValue.add(p.getSellPrice().multiply(new BigDecimal(p.getQuantity())));
                }
                totalItems = totalItems + p.getQuantity();
            }
        }
        return new BigDecimal[] {totalValue, BigDecimal.valueOf(totalItems)};
    }
}