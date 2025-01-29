package com.flowiee.pms.controller.sales;

import com.flowiee.pms.base.controller.BaseController;
import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.exception.EntityNotFoundException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.model.EximModel;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.payload.CreateOrderReq;
import com.flowiee.pms.model.payload.UpdateOrderReq;
import com.flowiee.pms.service.ExportService;
import com.flowiee.pms.service.sales.OrderPayService;
import com.flowiee.pms.service.sales.OrderReadService;
import com.flowiee.pms.service.sales.OrderWriteService;
import com.flowiee.pms.utils.constants.ErrorCode;
import com.flowiee.pms.utils.constants.OrderStatus;
import com.flowiee.pms.utils.constants.TemplateExport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/order")
@Tag(name = "Order API", description = "Quản lý đơn hàng")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderController extends BaseController {
    OrderReadService mvOrderReadService;
    OrderWriteService mvOrderWriteService;
    @Autowired
    @NonFinal
    @Qualifier("orderExportServiceImpl")
    ExportService mvExportService;
    OrderPayService mvOrderPayService;

    @Operation(summary = "Find all orders")
    @GetMapping("/all")
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public AppResponse<List<OrderDTO>> findAllOrders(@RequestParam(value = "orderId", required = false) Long pOrderId,
                                                     @RequestParam(value = "paymentMethodId", required = false) Long pPaymentMethodId,
                                                     @RequestParam(value = "orderStatusId", required = false) OrderStatus pOrderStatus,
                                                     @RequestParam(value = "salesChannelId", required = false) Long pSalesChannelId,
                                                     @RequestParam(value = "groupCustomerId", required = false) Long pGroupCustomerId,
                                                     @RequestParam(value = "sellerId", required = false) Long pSellerId,
                                                     @RequestParam(value = "customerId", required = false) Long pCustomerId,
                                                     @RequestParam(value = "branchId", required = false) Long pBranchId,
                                                     @RequestParam(value = "dateFilter", required = false) String pDateFilter,
                                                     @RequestParam(value = "txtSearch", required = false) String pTxtSearch,
                                                     @RequestParam("pageSize") int pageSize,
                                                     @RequestParam("pageNum") int pageNum) {
        try {
            Page<Order> orderPage = mvOrderReadService.findAll(pageSize, pageNum - 1, pTxtSearch, pOrderId, pPaymentMethodId, pOrderStatus, pSalesChannelId, pSellerId, pCustomerId, pBranchId, pGroupCustomerId, pDateFilter, null, null, null);
            return success(OrderDTO.fromOrders(orderPage.getContent()), pageNum, pageSize, orderPage.getTotalPages(), orderPage.getTotalElements());
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "order"), ex);
        }
    }

    @Operation(summary = "Find detail order")
    @GetMapping("/{orderId}")
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public AppResponse<OrderDTO> findOrderDetail(@PathVariable("orderId") Long pOrderId) {
        Order lvOrder = mvOrderReadService.findById(pOrderId, true);
        return success(OrderDTO.fromOrder(lvOrder));
    }

    @Operation(summary = "Create new order")
    @PostMapping("/insert")
    public AppResponse<OrderDTO> createOrder(@RequestBody CreateOrderReq request) {
        try {
            Order lvOrderCreated = mvOrderWriteService.createOrder(request);
            return success(OrderDTO.fromOrder(lvOrderCreated));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.CREATE_ERROR_OCCURRED.getDescription(), "order") + "\n" + ex.getMessage(), ex);
        }
    }

    @Operation(summary = "Update order")
    @PutMapping("/update/{orderId}")
    @PreAuthorize("@vldModuleSales.updateOrder(true)")
    public AppResponse<OrderDTO> update(@RequestBody UpdateOrderReq pRequest, @PathVariable("orderId") Long pOrderId) {
        Order lvOrderUpdated = mvOrderWriteService.updateOrder(pRequest, pOrderId);
        return success(OrderDTO.fromOrder(lvOrderUpdated));
    }

    @DeleteMapping("/delete/{orderId}")
    @PreAuthorize("@vldModuleSales.deleteOrder(true)")
    public AppResponse<String> deleteOrder(@PathVariable("orderId") Long orderId) {
        //Check them trang thai
        return success(mvOrderWriteService.deleteOrder(orderId));
    }

    @PutMapping("/do-pay/{orderId}")
    @PreAuthorize("@vldModuleSales.updateOrder(true)")
    public AppResponse<String> doPayOrder(@PathVariable("orderId") Long orderId,
                                          @RequestParam(value = "paymentTime", required = false) String paymentTime,
                                          @RequestParam("paymentMethod") Long paymentMethod,
                                          @RequestParam("paymentAmount") BigDecimal paymentAmount,
                                          @RequestParam(value = "paymentNote", required = false) String paymentNote) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
        return success(mvOrderPayService.doPay(orderId, LocalDateTime.parse(paymentTime, formatter), paymentMethod, paymentAmount, paymentNote));
    }

    @GetMapping("/export")
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public ResponseEntity<InputStreamResource> exportToExcel() {
        EximModel model = mvExportService.exportToExcel(TemplateExport.EX_LIST_OF_ORDERS, null, false);
        return ResponseEntity.ok().headers(model.getHttpHeaders()).body(model.getContent());
    }

    @GetMapping("/scan/QR-Code/{code}")
    public ModelAndView findOrderInfoByQRCode(@PathVariable("code") String pOrderCode) {
        try {
            Order lvOrder = mvOrderReadService.getOrderByCode(pOrderCode);
            if (lvOrder == null) {
                throw new EntityNotFoundException(new Object[]{"order"}, null, null);
            }
            return new ModelAndView().addObject("orderInfo", lvOrder);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(ErrorCode.SEARCH_ERROR_OCCURRED.getDescription(), "scan order"), ex);
        }
    }
}