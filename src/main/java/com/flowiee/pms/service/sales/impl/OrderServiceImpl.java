package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.config.TemplateSendEmail;
import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.sales.*;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.entity.system.SystemConfig;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.repository.category.CategoryRepository;
import com.flowiee.pms.repository.sales.CustomerRepository;
import com.flowiee.pms.repository.system.ConfigRepository;
import com.flowiee.pms.service.system.MailMediaService;
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

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends BaseService implements OrderService {
    private final CartService           mvCartService;
    private final OrderRepository       mvOrderRepository;
    private final ConfigRepository      mvConfigRepository;
    private final MailMediaService      mvMailMediaService;
    private final CartItemsService      mvCartItemsService;
    private final OrderItemsService     mvOrderItemsService;
    private final OrderQRCodeService    mvOrderQRCodeService;
    private final CategoryRepository    mvCategoryRepository;
    private final CustomerRepository    mvCustomerRepository;
    private final OrderHistoryService   mvOrderHistoryService;
    private final TicketImportService   mvTicketImportService;
    private final VoucherTicketService  mvVoucherTicketService;
    private final LedgerReceiptService  mvLedgerReceiptService;
    private final LedgerPaymentService  mvLedgerPaymentService;
    private final ProductVariantService mvProductVariantService;

    BigDecimal ONE_HUNDRED = new BigDecimal(100);

    @Override
    public List<OrderDTO> findAll() {
        return this.findAll(-1, -1, null, null, null, OrderStatus.ALL, null, null, null, null, null, null, null, null, null).getContent();
    }

    @Override
    public Page<OrderDTO> findAll(int pPageSize, int pPageNum, String pTxtSearch, Long pOrderId, Long pPaymentMethodId,
                                  OrderStatus pOrderStatus, Long pSalesChannelId, Long pSellerId, Long pCustomerId,
                                  Long pBranchId, Long pGroupCustomerId, String pDateFilter, LocalDateTime pOrderTimeFrom, LocalDateTime pOrderTimeTo, String pSortBy) {
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
        Page<Order> orders = mvOrderRepository.findAll(pTxtSearch, pOrderId, pPaymentMethodId, pOrderStatus, pSalesChannelId, pSellerId, pCustomerId, pBranchId, pGroupCustomerId, pOrderTimeFrom, pOrderTimeTo, pageable);
        return new PageImpl<>(OrderDTO.fromOrders(orders.getContent()), pageable, orders.getTotalElements());
    }

    @Override
    public Optional<OrderDTO> findById(Long orderId) {
        Optional<Order> orderOptional = mvOrderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(OrderDTO.fromOrder(orderOptional.get()));
    }

    @Transactional
    @Override
    public OrderDTO save(OrderDTO request) {
        Long lvPayMethodId = request.getPayMethodId();
        BigDecimal lvAmountDiscount = request.getAmountDiscount();
        String lvVoucherUsedCode = request.getVoucherUsedCode();
        Long lvCustomerId = request.getCustomerId();
        try {
            //Insert order
            Order order = Order.builder()
                .code(getNextOrderCode())
                .customer(new Customer(lvCustomerId))
                .kenhBanHang(new Category(request.getSalesChannelId(), null))
                .nhanVienBanHang(new Account(request.getCashierId()))
                .note(request.getNote())
                .orderTime(request.getOrderTime() != null ? request.getOrderTime() : LocalDateTime.now())
                //.trangThaiDonHang(new Category(request.getOrderStatusId(), null))
                .receiverName(request.getReceiverName())
                .receiverPhone(request.getReceiverPhone())
                .receiverEmail(request.getReceiverEmail())
                .receiverAddress(request.getReceiverAddress())
                .paymentMethod(lvPayMethodId != null ? new Category(lvPayMethodId, null) : null)
                .paymentStatus(false)
                .voucherUsedCode(ObjectUtils.isNotEmpty(lvVoucherUsedCode) ? lvVoucherUsedCode : null)
                .amountDiscount(lvAmountDiscount != null ? lvAmountDiscount : BigDecimal.ZERO)
                .packagingCost(BigDecimal.ZERO)
                .shippingCost(BigDecimal.ZERO)
                .giftWrapCost(BigDecimal.ZERO)
                .codFee(BigDecimal.ZERO)
                .isGiftWrapped(false)
                .orderStatus(getDefaultOrderStatus())
                .build();
            Order orderSaved = mvOrderRepository.save(order);

            //QRCode
            mvOrderQRCodeService.saveQRCodeOfOrder(orderSaved.getId());

            //Insert items detail
            BigDecimal totalAmountOfOrder = BigDecimal.ZERO;
            Optional<OrderCart> cart = mvCartService.findById(request.getCartId());
            if (cart.isPresent()) {
                for (Items items : cart.get().getListItems()) {
                    Long lvProductVariantId = items.getProductDetail().getId();
                    BigDecimal lvExtraDiscount = items.getExtraDiscount();
                    Optional<ProductVariantDTO> productDetail = mvProductVariantService.findById(lvProductVariantId);
                    if (productDetail.isPresent()) {
                        int lvItemQuantity = mvCartItemsService.findQuantityOfItem(items.getOrderCart().getId() , lvProductVariantId);
                        OrderDetail orderDetail = OrderDetail.builder()
                            .order(orderSaved)
                            .productDetail(productDetail.get())
                            .quantity(lvItemQuantity)
                            .status(true)
                            .note(items.getNote())
                            .price(items.getPrice())
                            .priceOriginal(items.getPriceOriginal())
                            .extraDiscount(lvExtraDiscount != null ? lvExtraDiscount : BigDecimal.ZERO)
                            .priceType(items.getPriceType())
                            .build();
                        OrderDetail orderDetailSaved = mvOrderItemsService.save(orderDetail);

                        totalAmountOfOrder = totalAmountOfOrder.add(orderDetailSaved.getPrice().multiply(BigDecimal.valueOf(lvItemQuantity)));
                    }
                }
            }

            //Update voucher ticket used status
            VoucherTicket voucherTicket = mvVoucherTicketService.findTicketByCode((lvVoucherUsedCode));
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
                BigDecimal bonusPoints = totalAmountOfOrder.subtract(orderSaved.getAmountDiscount()).divide(ONE_HUNDRED).setScale(0, BigDecimal.ROUND_DOWN);
                mvCustomerRepository.updateBonusPoint(lvCustomerId, bonusPoints.intValue());
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

    @Transactional
    @Override
    public OrderDTO update(OrderDTO dto, Long id) {
        Order orderToUpdate = mvOrderRepository.findById(id).orElse(null);
        if (orderToUpdate == null) {
            throw new ResourceNotFoundException("Order not found!");
        }
        Order orderBefore = ObjectUtils.clone(orderToUpdate);
        orderToUpdate.setNote(dto.getNote());
        //orderToUpdate.setTrangThaiDonHang(new Category(dto.getOrderStatusId()));
        orderToUpdate.setOrderStatus(dto.getOrderStatus());
        Order orderUpdated = mvOrderRepository.save(orderToUpdate);

        //After updated
        OrderStatus lvOrderStatus = orderUpdated.getOrderStatus();
        Long lvStorageId = orderUpdated.getTicketExport().getStorage().getId();
        boolean isNeedRefund = false;
        switch (lvOrderStatus) {
            case CNCL:
                SystemConfig lvSendNotifyConfig = mvConfigRepository.findByCode(ConfigCode.sendNotifyCustomerOnOrderConfirmation.name());
                if (lvSendNotifyConfig != null && lvSendNotifyConfig.isYesOption())
                    sendNotifyCustomerOnOrderConfirmation(orderUpdated);
                break;
            case RTND:
                mvTicketImportService.restockReturnedItems(lvStorageId, orderUpdated.getCode());
                if (isNeedRefund) {
                    //create ledger transaction record for export

                }
                break;
        }

        //Log
        ChangeLog changeLog = new ChangeLog(orderBefore, orderUpdated);
        mvOrderHistoryService.save(changeLog.getLogChanges(), "Cập nhật đơn hàng", id, null);
        systemLogService.writeLogUpdate(MODULE.SALES, ACTION.PRO_ORD_U, MasterObject.Order, "Cập nhật đơn hàng", changeLog);
        logger.info("Cập nhật đơn hàng {}", dto.toString());

        return OrderDTO.fromOrder(orderToUpdate);
    }

    @Override
    public String delete(Long id) {
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
    public String doPay(Long orderId, LocalDateTime paymentTime, Long paymentMethod, Float paymentAmount, String paymentNote) {
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
                .amount(dto.get().calTotalAmountDiscount())
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

    private void sendNotifyCustomerOnOrderConfirmation(Order pOrderInfo) {
        String lvDestination = pOrderInfo.getReceiverEmail();
        if (pOrderInfo.getReceiverEmail() == null || pOrderInfo.getReceiverEmail().isBlank())
            return;

        Map<String, Object> lvNotificationParameter = new HashMap<>();
        lvNotificationParameter.put(NotificationType.SendNotifyCustomerOnOrderConfirmation.name(), lvDestination);
        lvNotificationParameter.put("customerName", pOrderInfo.getCustomer().getCustomerName());
        lvNotificationParameter.put("orderTime", pOrderInfo.getOrderTime().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")));
        lvNotificationParameter.put("deliveryAddress", pOrderInfo.getReceiverAddress());
        lvNotificationParameter.put("deliveryPhoneNumber", pOrderInfo.getReceiverPhone());
        lvNotificationParameter.put("deliveryEmail", pOrderInfo.getReceiverEmail());
        lvNotificationParameter.put("totalOrderValue", pOrderInfo.calTotalAmountDiscount());

        StringBuilder lvRowsBuilder = new StringBuilder("");
        StringBuilder lvRowBuilder = new StringBuilder();
        int i = 1;
        for (OrderDetail lvItem : pOrderInfo.getListOrderDetail()) {
            ProductDetail lvProductDetail = lvItem.getProductDetail();
            BigDecimal lvUnitPrice = lvItem.getPrice();
            int lvQuantity = lvItem.getQuantity();
            lvRowBuilder.append("<tr>");
            lvRowBuilder.append("<td>").append(i++).append("</td>");
            lvRowBuilder.append("<td>").append(lvProductDetail.getVariantName()).append("</td>");
            lvRowBuilder.append("<td>").append(lvUnitPrice).append("</td>");
            lvRowBuilder.append("<td>").append(lvQuantity).append("</td>");
            lvRowBuilder.append("<td>").append(lvUnitPrice.multiply(new BigDecimal(lvQuantity))).append("</td>");
            lvRowBuilder.append("<td>").append(lvItem.getNote()).append("</td>");
            lvRowBuilder.append("</tr>");
            lvRowsBuilder.append(lvRowBuilder.toString());
            lvRowBuilder.setLength(0);
        }
        lvNotificationParameter.put("orderDetails", lvRowsBuilder.toString());

        mvMailMediaService.send(NotificationType.SendNotifyCustomerOnOrderConfirmation, lvNotificationParameter);
    }

    private OrderStatus getDefaultOrderStatus() {
        return OrderStatus.PEND;
    }
}