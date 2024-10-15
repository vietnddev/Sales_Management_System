package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.*;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.repository.category.CategoryRepository;
import com.flowiee.pms.repository.sales.CustomerRepository;
import com.flowiee.pms.utils.ChangeLog;
import com.flowiee.pms.utils.constants.*;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.*;
import com.flowiee.pms.utils.*;
import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.repository.sales.OrderRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderServiceImpl extends BaseService implements OrderService {
    CartService mvCartService;
    OrderRepository    mvOrderRepository;
    CartItemsService   mvCartItemsService;
    OrderItemsService  mvOrderItemsService;
    OrderQRCodeService mvOrderQRCodeService;
    CategoryRepository mvCategoryRepository;
    CustomerRepository mvCustomerRepository;
    OrderHistoryService   mvOrderHistoryService;
    VoucherTicketService  mvVoucherTicketService;
    LedgerReceiptService  mvLedgerReceiptService;
    ProductVariantService mvProductVariantService;

    @Override
    public List<OrderDTO> findAll() {
        return this.findAll(-1, -1, null, null, null, null, null, null, null, null, null, null, null, null, null).getContent();
    }

    @Override
    public Page<OrderDTO> findAll(int pPageSize, int pPageNum, String pTxtSearch, Integer pOrderId, Integer pPaymentMethodId,
                                  Integer pOrderStatusId, Integer pSalesChannelId, Integer pSellerId, Integer pCustomerId,
                                  Integer pBranchId, Integer pGroupCustomerId, String pDateFilter, LocalDateTime pOrderTimeFrom, LocalDateTime pOrderTimeTo, String pSortBy) {
        Pageable pageable = Pageable.unpaged();
        if (pPageSize >= 0 && pPageNum >= 0) {
            pageable = PageRequest.of(pPageNum, pPageSize, Sort.by(pSortBy != null ? pSortBy : "orderTime").descending());
        }
        if (pOrderTimeFrom == null) {
            pOrderTimeFrom = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
        }
        if (pOrderTimeTo == null) {
            pOrderTimeTo = LocalDateTime.of(2100, 12, 1, 0, 0, 0);
        }
        if (pDateFilter != null && !"".equals(pDateFilter)) {
            LocalDateTime[] lvFromDateToDate = getFromDateToDate(pOrderTimeFrom, pOrderTimeTo, pDateFilter);
            pOrderTimeFrom = lvFromDateToDate[0];
            pOrderTimeTo = lvFromDateToDate[1];
        }
        Page<Order> orders = mvOrderRepository.findAll(pTxtSearch, pOrderId, pPaymentMethodId, pOrderStatusId, pSalesChannelId, pSellerId, pCustomerId, pBranchId, pGroupCustomerId, pOrderTimeFrom, pOrderTimeTo, pageable);
        return new PageImpl<>(OrderDTO.fromOrders(orders.getContent()), pageable, orders.getTotalElements());
    }

    @Override
    public Optional<OrderDTO> findById(Integer orderId) {
        Optional<Order> orderOptional = mvOrderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(OrderDTO.fromOrder(orderOptional.get()));
    }

    @Transactional
    @Override
    public OrderDTO save(OrderDTO request) {
        try {
            //Insert order
            Order order = Order.builder()
                .code(getNextOrderCode())
                .customer(new Customer(request.getCustomerId()))
                .kenhBanHang(new Category(request.getSalesChannelId(), null))
                .nhanVienBanHang(new Account(request.getCashierId()))
                .note(request.getNote())
                .orderTime(request.getOrderTime() != null ? request.getOrderTime() : LocalDateTime.now())
                .trangThaiDonHang(new Category(request.getOrderStatusId(), null))
                .receiverName(request.getReceiverName())
                .receiverPhone(request.getReceiverPhone())
                .receiverEmail(request.getReceiverEmail())
                .receiverAddress(request.getReceiverAddress())
                .paymentMethod(request.getPayMethodId() != null ? new Category(request.getPayMethodId(), null) : null)
                .paymentStatus(false)
                .voucherUsedCode(ObjectUtils.isNotEmpty(request.getVoucherUsedCode()) ? request.getVoucherUsedCode() : null)
                .amountDiscount(request.getAmountDiscount() != null ? request.getAmountDiscount() : BigDecimal.ZERO)
                .packagingCost(BigDecimal.ZERO)
                .shippingCost(BigDecimal.ZERO)
                .giftWrapCost(BigDecimal.ZERO)
                .codFee(BigDecimal.ZERO)
                .isGiftWrapped(false)
                .build();
            Order orderSaved = mvOrderRepository.save(order);

            //QRCode
            mvOrderQRCodeService.saveQRCodeOfOrder(orderSaved.getId());

            //Insert items detail
            BigDecimal totalAmountOfOrder = BigDecimal.ZERO;
            Optional<OrderCart> cart = mvCartService.findById(request.getCartId());
            if (cart.isPresent()) {
                for (Items items : cart.get().getListItems()) {
                    Optional<ProductVariantDTO> productDetail = mvProductVariantService.findById(items.getProductDetail().getId());
                    if (productDetail.isPresent()) {
                        OrderDetail orderDetail = OrderDetail.builder()
                            .order(orderSaved)
                            .productDetail(productDetail.get())
                            .quantity(mvCartItemsService.findQuantityOfItem(items.getOrderCart().getId() , productDetail.get().getId()))
                            .status(true)
                            .note(items.getNote())
                            .price(items.getPrice())
                            .priceOriginal(items.getPriceOriginal())
                            .extraDiscount(items.getExtraDiscount() != null ? items.getExtraDiscount() : BigDecimal.ZERO)
                            .priceType(items.getPriceType())
                            .build();
                        OrderDetail orderDetailSaved = mvOrderItemsService.save(orderDetail);

                        totalAmountOfOrder = totalAmountOfOrder.add(orderDetailSaved.getPrice().multiply(BigDecimal.valueOf(orderDetailSaved.getQuantity())));
                    }
                }
            }

            //Update voucher ticket used status
            VoucherTicket voucherTicket = mvVoucherTicketService.findTicketByCode((request.getVoucherUsedCode()));
            if (voucherTicket != null) {
//                String statusCode = voucherService.checkTicketToUse(request.getVoucherUsedCode());
//                if (AppConstants.VOUCHER_STATUS.ACTIVE.name().equals(statusCode)) {
//                    orderSaved.setVoucherUsedCode(request.getVoucherUsedCode());
//                    orderSaved.setAmountDiscount(request.getAmountDiscount());
//                }
                voucherTicket.setCustomer(orderSaved.getCustomer());
                voucherTicket.setActiveTime(new Date());
                voucherTicket.setUsed(true);
                mvVoucherTicketService.update(voucherTicket, voucherTicket.getId());
            }
            //orderRepo.save(orderSaved);

            //Accumulate bonus points for customer
            if (request.getAccumulateBonusPoints() != null && request.getAccumulateBonusPoints()) {
                BigDecimal bonusPoints = totalAmountOfOrder.subtract(orderSaved.getAmountDiscount()).divide(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_DOWN);
                mvCustomerRepository.updateBonusPoint(orderSaved.getCustomer().getId(), bonusPoints.intValue());
            }

            //Sau khi đã lưu đơn hàng thì xóa all items
            mvCartItemsService.deleteAllItems();

            //Log
            systemLogService.writeLogCreate(MODULE.PRODUCT, ACTION.PRO_ORD_C, MasterObject.Order, "Thêm mới đơn hàng", orderSaved.getCode());
            logger.info("Insert new order success! insertBy={}", CommonUtils.getUserPrincipal().getUsername());

            return OrderDTO.fromOrder(orderSaved);
        } catch (RuntimeException ex) {
            throw new AppException("Insert new order fail! order=" + request, ex);
        }
    }

    @Override
    public OrderDTO update(OrderDTO dto, Integer id) {
        Order orderToUpdate = mvOrderRepository.findById(id).orElse(null);
        if (orderToUpdate == null) {
            throw new ResourceNotFoundException("Order not found!");
        }
        Order orderBefore = ObjectUtils.clone(orderToUpdate);
        orderToUpdate.setNote(dto.getNote());
        orderToUpdate.setTrangThaiDonHang(mvCategoryRepository.findById(dto.getOrderStatusId()).get());
        Order orderUpdated = mvOrderRepository.save(orderToUpdate);

        ChangeLog changeLog = new ChangeLog(orderBefore, orderUpdated);
        mvOrderHistoryService.save(changeLog.getLogChanges(), "Cập nhật đơn hàng", id, null);
        systemLogService.writeLogUpdate(MODULE.SALES, ACTION.PRO_ORD_U, MasterObject.Order, "Cập nhật đơn hàng", changeLog);
        logger.info("Cập nhật đơn hàng {}", dto.toString());

        return OrderDTO.fromOrder(orderToUpdate);
    }

    @Override
    public String delete(Integer id) {
        Optional<OrderDTO> order = this.findById(id);
        if (order.isEmpty()) {
            throw new ResourceNotFoundException("Order not found!");
        }
        if (order.get().getPaymentStatus()) {
            throw new DataInUseException(ErrorCode.ERROR_DATA_LOCKED.getDescription());
        }
        mvOrderRepository.deleteById(id);
        systemLogService.writeLogDelete(MODULE.PRODUCT, ACTION.PRO_ORD_D, MasterObject.Order, "Xóa đơn hàng", order.toString());
        logger.info("Xóa đơn hàng orderId={}", id);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Transactional
    @Override
    public String doPay(Integer orderId, LocalDateTime paymentTime, Integer paymentMethod, Float paymentAmount, String paymentNote) {
        Optional<OrderDTO> dto = this.findById(orderId);
        if (dto.isEmpty()) {
            throw new ResourceNotFoundException("Order not found!");
        }
        if (dto.get().getPaymentStatus()) {
            throw new BadRequestException("The order has been paid");
        }
        if ((paymentMethod == null || paymentMethod <= 0) || paymentAmount == null) {
            throw new BadRequestException("Thông tin thanh toán không hợp lệ!");
        }
        if (paymentTime == null) paymentTime = LocalDateTime.now();
        mvOrderRepository.updatePaymentStatus(orderId, paymentTime, paymentMethod, paymentAmount, paymentNote);

        logger.info("Begin generate receipt issued when completed an order");
        Category groupObject = mvCategoryRepository.findByTypeAndCode(CategoryType.GROUP_OBJECT.name(), "KH");//Customer
        Category receiptType = mvCategoryRepository.findByTypeAndCode(CategoryType.RECEIPT_TYPE.name(), "PO");//Payment for order
        mvLedgerReceiptService.save(LedgerTransaction.builder()
                .tranType(LedgerTranType.PT.name())
                .groupObject(groupObject)
                .tranContent(receiptType)
                .paymentMethod(dto.get().getPaymentMethod())
                .fromToName(dto.get().getCustomer().getCustomerName())
                .amount(Order.calTotalAmount(dto.get().getListOrderDetail()))
                .build());
        logger.info("End generate receipt issued when completed an order");

        systemLogService.writeLogUpdate(MODULE.SALES, ACTION.PRO_ORD_U, MasterObject.Order, "Cập nhật trạng thái thanh toán đơn hàng", "Số tiền: " + CommonUtils.formatToVND(paymentAmount));
        return MessageCode.UPDATE_SUCCESS.getDescription();
    }

    @Override
    public List<Order> findOrdersToday() {
        return mvOrderRepository.findOrdersToday();
    }

    private String getNextOrderCode() {
        int orderTodayQty = 0;
        List<Order> ordersToday = mvOrderRepository.findOrdersToday();
        if (ordersToday != null) {
            orderTodayQty = ordersToday.size();
        }
        LocalDate currentDate = LocalDate.now();
        String year = String.valueOf(currentDate.getYear());
        String month = String.format("%02d", currentDate.getMonthValue());
        String day = String.format("%02d", currentDate.getDayOfMonth());
        return year + month + day + String.format("%03d", orderTodayQty + 1);
    }

    private LocalDateTime[] getFromDateToDate(LocalDateTime pFromDate, LocalDateTime pToDate, String pFilterDate) {
        LocalDateTime lvStartTime = null;
        LocalDateTime lvEndTime = null;

        LocalDate today = LocalDate.now();
        LocalDateTime startOfToDay = today.atTime(LocalTime.MIN);
        LocalDateTime endOfToDay = today.atTime(LocalTime.MAX);

        YearMonth yearMonth = YearMonth.of(today.getYear(), today.getMonthValue());
        LocalDateTime startDayOfMonth = yearMonth.atDay(1).atTime(LocalTime.MIN);
        LocalDateTime endDayOfMonth = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);

        switch (pFilterDate) {
            case "T0": //Today
                pFromDate = startOfToDay;
                pToDate = endOfToDay;
                break;
            case "T-1": //Previous day
                pFromDate = startOfToDay.minusDays(1);
                pToDate = endOfToDay.minusDays(1);
                break;
            case "T-7": //7 days ago
                pFromDate = startOfToDay.minusDays(7);
                pToDate = endOfToDay;
                break;
            case "M0": //This month
                pFromDate = startDayOfMonth;
                pToDate = endDayOfMonth;
                break;
            case "M-1": //Previous month
                pFromDate = startDayOfMonth.minusMonths(1);
                pToDate = endDayOfMonth.minusMonths(1);
        }

        lvStartTime = pFromDate;
        lvEndTime = pToDate;

        return new LocalDateTime[] {lvStartTime, lvEndTime};
    }
}