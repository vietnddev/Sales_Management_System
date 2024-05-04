package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.*;
import com.flowiee.pms.entity.system.Account;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.model.dto.OrderDetailDTO;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.repository.sales.OrderHistoryRepository;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.*;
import com.flowiee.pms.utils.*;
import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.repository.sales.OrderRepository;
import com.flowiee.pms.service.system.SystemLogService;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepo;
    private final ProductVariantService productVariantService;
    private final SystemLogService systemLogService;
    private final CartService cartService;
    private final CartItemsService cartItemsService;
    private final OrderHistoryRepository orderHistoryRepo;
    private final OrderItemsService orderItemsService;
    private final OrderQRCodeService orderQRCodeService;
    private final VoucherTicketService voucherTicketService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepo, SystemLogService systemLogService, CartService cartService,
                            CartItemsService cartItemsService, OrderHistoryRepository orderHistoryRepo, ProductVariantService productVariantService,
                            OrderQRCodeService orderQRCodeService, VoucherTicketService voucherTicketService, OrderItemsService orderItemsService) {
        this.orderRepo = orderRepo;
        this.systemLogService = systemLogService;
        this.cartService = cartService;
        this.cartItemsService = cartItemsService;
        this.orderHistoryRepo = orderHistoryRepo;
        this.orderItemsService = orderItemsService;
        this.productVariantService = productVariantService;
        this.orderQRCodeService = orderQRCodeService;
        this.voucherTicketService = voucherTicketService;
    }

    @Override
    public List<OrderDTO> findAll() {
        return this.findAll(-1, -1, null, null, null, null, null, null).getContent();
    }

    @Override
    public Page<OrderDTO> findAll(int pageSize, int pageNum, Integer pOrderId, Integer pPaymentMethodId,
                                  Integer pOrderStatusId, Integer pSalesChannelId, Integer pSellerId, Integer pCustomerId) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("orderTime").descending());
        }
        Page<Order> orders = orderRepo.findAll(pOrderId, pPaymentMethodId, pOrderStatusId, pSalesChannelId, pSellerId, pCustomerId, pageable);
        return new PageImpl<>(OrderDTO.fromOrders(orders.getContent()), pageable, orders.getTotalElements());
    }

    @Override
    public Optional<OrderDTO> findById(Integer orderId) {
        OrderDTO order = OrderDTO.fromOrder(Objects.requireNonNull(orderRepo.findById(orderId).orElse(null)));
        List<OrderDetail> listOrderDetail = orderItemsService.findByOrderId(orderId);
        int totalProduct = 0;
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderDetail d : listOrderDetail) {
            totalProduct += d.getQuantity();
            totalAmount = totalAmount.add(d.getPrice().multiply(BigDecimal.valueOf(d.getQuantity())));
        }
        order.setListOrderDetailDTO(OrderDetailDTO.fromOrderDetails(listOrderDetail));
        order.setTotalProduct(totalProduct);
        order.setTotalAmount(totalAmount);
        order.setTotalAmountDiscount(order.getTotalAmount().subtract(order.getAmountDiscount()));
        return Optional.of(order);
    }

    @Transactional
    @Override
    public OrderDTO save(OrderDTO request) {
        try {
            String orderCode = "F" + CommonUtils.now("yyMMddHHmmss");
            //Insert order
            Order order = new Order();
            order.setCode(orderCode);
            order.setCustomer(new Customer(request.getCustomerId()));
            order.setKenhBanHang(new Category(request.getSalesChannelId(), null));
            order.setNhanVienBanHang(new Account(request.getCashierId()));
            order.setNote(request.getNote());
            if (request.getOrderTime() != null) {
                order.setOrderTime(request.getOrderTime());
            } else {
                order.setOrderTime(LocalDateTime.now());
            }
            order.setTrangThaiDonHang(new Category(request.getOrderStatusId(), null));
            order.setReceiverName(request.getReceiverName());
            order.setReceiverPhone(request.getReceiverPhone());
            order.setReceiverEmail(request.getReceiverEmail());
            order.setReceiverAddress(request.getReceiverAddress());
            if (request.getPayMethodId() != null) {
                order.setPaymentMethod(new Category(request.getPayMethodId(), null));
            }
            order.setPaymentStatus(false);

            if (ObjectUtils.isNotEmpty(request.getVoucherUsedCode())) {
                order.setVoucherUsedCode(request.getVoucherUsedCode());
            }
            if (ObjectUtils.isNotEmpty(request.getAmountDiscount())) {
                order.setAmountDiscount(request.getAmountDiscount());
            }

            Order orderSaved = orderRepo.save(order);

            //QRCode
            orderQRCodeService.saveQRCodeOfOrder(orderSaved.getId());

            //Insert items detail
            Optional<OrderCart> cart = cartService.findById(request.getCartId());
            if (cart.isPresent()) {
                for (Items items : cart.get().getListItems()) {
                    Optional<ProductVariantDTO> productDetail = productVariantService.findById(items.getProductDetail().getId());
                    if (productDetail.isPresent()) {
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setOrder(orderSaved);
                        orderDetail.setProductDetail(productDetail.get());
                        orderDetail.setQuantity(cartItemsService.findQuantityOfItem(items.getOrderCart().getId() , productDetail.get().getId()));
                        orderDetail.setStatus(true);
                        orderDetail.setGhiChu(items.getNote());
                        orderDetail.setPrice(productDetail.get().getDiscountPrice());
                        orderDetail.setPriceOriginal(productDetail.get().getOriginalPrice());
                        orderItemsService.save(orderDetail);
                    }
                }
            }

            //Update voucher ticket status
            VoucherTicket voucherTicket = voucherTicketService.findTicketByCode((request.getVoucherUsedCode()));
            if (voucherTicket != null) {
//                String statusCode = voucherService.checkTicketToUse(request.getVoucherUsedCode());
//                if (AppConstants.VOUCHER_STATUS.ACTIVE.name().equals(statusCode)) {
//                    orderSaved.setVoucherUsedCode(request.getVoucherUsedCode());
//                    orderSaved.setAmountDiscount(request.getAmountDiscount());
//                }
                voucherTicket.setCustomer(orderSaved.getCustomer());
                voucherTicket.setActiveTime(new Date());
                voucherTicket.setUsed(true);
                voucherTicketService.update(voucherTicket, voucherTicket.getId());
            }
            //orderRepo.save(orderSaved);

            //Sau khi đã lưu đơn hàng thì xóa all items
            cartItemsService.deleteAllItems();

            //Log
            systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_ORD_C.name(), "Thêm mới đơn hàng: " + order.toString());
            logger.info("Insert new order success! insertBy={}", CommonUtils.getUserPrincipal().getUsername());

            return OrderDTO.fromOrder(orderSaved);
        } catch (RuntimeException ex) {
            throw new AppException("Insert new order fail! order=" + request, ex);
        }
    }

    @Override
    public OrderDTO update(OrderDTO dto, Integer id) {
        Order orderToUpdate = orderRepo.findById(id).orElse(null);
        if (orderToUpdate == null) {
            throw new BadRequestException();
        }
        for (Map.Entry<String, String> entry : orderToUpdate.compareTo(dto).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            orderHistoryRepo.save(new OrderHistory(id, null, "Update order", key, value.substring(0, value.indexOf("#")), value.substring(value.indexOf("#") + 1)));
        }
        orderToUpdate.setId(id);
        orderToUpdate.setNote(dto.getNote());
        orderToUpdate.setPaymentNote(dto.getPaymentNote());
        orderToUpdate.setKenhBanHang(dto.getKenhBanHang());
        orderToUpdate.setPaymentMethod(dto.getPaymentMethod());
        orderToUpdate.setPaymentStatus(dto.getPaymentStatus());
        orderRepo.save(orderToUpdate);
        systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_ORD_U.name(), "Cập nhật đơn hàng: " + dto.toString());
        logger.info(OrderServiceImpl.class.getName() + ": Cập nhật đơn hàng " + dto.toString());
        return OrderDTO.fromOrder(orderToUpdate);
    }

    @Override
    public String delete(Integer id) {
        Optional<OrderDTO> order = this.findById(id);
        if (order.isEmpty() || order.get().getPaymentStatus()) {
            throw new DataInUseException(MessageUtils.ERROR_DATA_LOCKED);
        }
        orderRepo.deleteById(id);
        systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_ORD_D.name(), "Xóa đơn hàng: " + order.toString());
        logger.info(OrderServiceImpl.class.getName() + ": Xóa đơn hàng " + order.toString());
        return MessageUtils.DELETE_SUCCESS;
    }

    @Transactional
    @Override
    public String doPay(Integer orderId, LocalDateTime paymentTime, Integer paymentMethod, Float paymentAmount, String paymentNote) {
        orderRepo.updatePaymentStatus(orderId, paymentTime, paymentMethod, paymentAmount, paymentNote);
        return "Thanh toán thành công!";
    }

    @Override
    public List<Order> findOrdersToday() {
        return orderRepo.findOrdersToday();
    }
}