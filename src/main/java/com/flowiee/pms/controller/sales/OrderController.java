package com.flowiee.pms.controller.sales;

import com.flowiee.pms.base.BaseController;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.service.sales.OrderExportService;
import com.flowiee.pms.service.sales.OrderService;
import com.flowiee.pms.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${app.api.prefix}/order")
@Tag(name = "Order API", description = "Quản lý đơn hàng")
public class OrderController extends BaseController {
    private final OrderService orderService;
    private final OrderExportService orderExportService;

    @Autowired
    public OrderController(OrderService orderService, OrderExportService orderExportService) {
        this.orderService = orderService;
        this.orderExportService = orderExportService;
    }

    @Operation(summary = "Find all orders")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public AppResponse<List<OrderDTO>> findAllOrders(@RequestParam(value = "orderId", required = false) Integer pOrderId,
                                                     @RequestParam(value = "paymentMethodId", required = false) Integer pPaymentMethodId,
                                                     @RequestParam(value = "orderStatusId", required = false) Integer pOrderStatusId,
                                                     @RequestParam(value = "salesChannelId", required = false) Integer pSalesChannelId,
                                                     @RequestParam(value = "sellerId", required = false) Integer pSellerId,
                                                     @RequestParam(value = "customerId", required = false) Integer pCustomerId,
                                                     @RequestParam("pageSize") int pageSize,
                                                     @RequestParam("pageNum") int pageNum) {
        try {
            Page<OrderDTO> orderPage = orderService.findAll(pageSize, pageNum - 1, pOrderId, pPaymentMethodId, pOrderStatusId, pSalesChannelId, pSellerId, pCustomerId, null, null, null);
            return success(orderPage.getContent(), pageNum, pageSize, orderPage.getTotalPages(), orderPage.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "order"), ex);
        }
    }

    @Operation(summary = "Find detail order")
    @GetMapping("/{orderId}")
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public AppResponse<OrderDTO> findOrderDetail(@PathVariable("orderId") Integer orderId) {
        try {
            Optional<OrderDTO> orderDTO = orderService.findById(orderId);
            if (orderDTO.isEmpty()) {
                throw new BadRequestException();
            }
            return success(orderDTO.get());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "order"), ex);
        }
    }

    @Operation(summary = "Create new order")
    @PostMapping("/insert")
    public AppResponse<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            return success(orderService.save(orderDTO));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "order"), ex);
        }
    }

    @PutMapping("/update/{orderId}")
    @PreAuthorize("@vldModuleSales.updateOrder(true)")
    public AppResponse<String> update(@RequestBody OrderDTO order, @PathVariable("orderId") Integer orderId) {
        try {
            if (orderId <= 0 || order == null || orderService.findById(orderId).isEmpty()) {
                throw new BadRequestException();
            }
            orderService.update(order, orderId);
            return success(MessageUtils.UPDATE_SUCCESS);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "order"), ex);
        }
    }

    @DeleteMapping("/delete/{orderId}")
    @PreAuthorize("@vldModuleSales.deleteOrder(true)")
    public AppResponse<String> deleteOrder(@PathVariable("orderId") Integer orderId) {
        try {
            //Check them trang thai
            return success(orderService.delete(orderId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "order"), ex);
        }
    }

    @PutMapping("/do-pay/{orderId}")
    @PreAuthorize("@vldModuleSales.updateOrder(true)")
    public AppResponse<String> doPayOrder(@PathVariable("orderId") Integer orderId,
                                          @RequestParam(value = "paymentTime", required = false) LocalDateTime paymentTime,
                                          @RequestParam("paymentMethod") Integer paymentMethod,
                                          @RequestParam("paymentAmount") Float paymentAmount,
                                          @RequestParam(value = "paymentNote", required = false) String paymentNote) {
        try {
            if (orderId == null || orderId <= 0 || orderService.findById(orderId).isEmpty()) {
                throw new BadRequestException();
            }
            if (paymentTime == null) {
                paymentTime = LocalDateTime.now();
            }
            if (paymentMethod <= 0) {
                throw new BadRequestException("Hình thức thanh toán không hợp lệ!");
            }
            return success(orderService.doPay(orderId, paymentTime, paymentMethod, paymentAmount, paymentNote));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "pay order"), ex);
        }
    }

    @GetMapping("/export")
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public ResponseEntity<?> exportToExcel() {
        try {
            return orderExportService.exportToExcel(null, null, true);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "export order"), ex);
        }
    }

    @GetMapping("/scan/QR-Code/{code}")
    public ModelAndView findOrderInfoByQRCode(@PathVariable("code") String code) {
        try {
            //Xử lý code thành id
            return new ModelAndView().addObject("orderInfo", orderService.findById(null));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "scan order"), ex);
        }
    }
}