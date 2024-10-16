package com.flowiee.pms.controller.sales;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.sales.OrderDetail;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.service.sales.*;
import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.exception.ResourceNotFoundException;

import com.flowiee.pms.utils.constants.CategoryType;
import com.flowiee.pms.utils.constants.Pages;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderControllerView extends BaseController {
    OrderService mvOrderService;
    CategoryService mvCategoryService;
    OrderItemsService mvOrderItemsService;
    VoucherTicketService mvVoucherTicketService;
    OrderPrintInvoiceService mvPrintInvoiceService;

    List<String> mvOrderStatusCanModifyItem = List.of("PRP", "W4D");
    List<String> mvOrderStatusCanDeleteItem = List.of("PRP");
    List<String> mvOrderStatusDoesNotAllowModify = List.of("DONE", "CAN");

    @GetMapping
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public ModelAndView viewAllOrders() {
        setupSearchTool(true, List.of("BRANCH", CategoryType.GROUP_CUSTOMER, CategoryType.PAYMENT_METHOD, CategoryType.ORDER_STATUS, CategoryType.SALES_CHANNEL, "DATE_FILTER"));
        return baseView(new ModelAndView(Pages.PRO_ORDER.getTemplate()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public ModelAndView findDonHangDetail(@PathVariable("id") Long orderId) {
        Optional<OrderDTO> orderDetail = mvOrderService.findById(orderId);
        if (orderId <= 0 || orderDetail.isEmpty()) {
            throw new ResourceNotFoundException("Order not found!");
        }
        List<Category> orderStatus = new ArrayList<>(List.of(new Category(orderDetail.get().getOrderStatusId(), orderDetail.get().getOrderStatusName())));
        orderStatus.addAll(mvCategoryService.findOrderStatus(orderDetail.get().getOrderStatusId()));

        ModelAndView modelAndView = new ModelAndView(Pages.PRO_ORDER_DETAIL.getTemplate());
        modelAndView.addObject("orderDetailId", orderId);
        modelAndView.addObject("orderDetail", orderDetail.get());
        modelAndView.addObject("listOrderDetail", orderDetail.get().getListOrderDetailDTO());
        modelAndView.addObject("listPaymentMethod", mvCategoryService.findSubCategory(CategoryType.PAYMENT_METHOD, null, null, -1, -1).getContent());
        modelAndView.addObject("orderStatus", orderStatus);
        modelAndView.addObject("allowEditItem", mvOrderStatusCanModifyItem.contains(orderDetail.get().getTrangThaiDonHang().getCode()));
        modelAndView.addObject("allowEditGeneral", !mvOrderStatusDoesNotAllowModify.contains(orderDetail.get().getTrangThaiDonHang().getCode()));
        modelAndView.addObject("allowDoPay", !mvOrderStatusDoesNotAllowModify.contains(orderDetail.get().getTrangThaiDonHang().getCode()));

        return baseView(modelAndView);
    }
    
    @PostMapping("/ban-hang/cart/add-voucher/{code}")
    @PreAuthorize("@vldModuleSales.readVoucher(true)")
    public ModelAndView checkToUse(@PathVariable("code") String code) {
        ModelAndView modelAndView = new ModelAndView("redirect:/order/ban-hang");
        modelAndView.addObject("ticket_code", code);
        modelAndView.addObject("ticket_status", mvVoucherTicketService.checkTicketToUse(code));
        modelAndView.addObject("ticket_info", mvVoucherTicketService.findTicketByCode(code));
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("@vldModuleSales.deleteOrder(true)")
    public ModelAndView delete(@PathVariable("id") Long orderId) {
        mvOrderService.delete(orderId);
        return new ModelAndView("redirect:/order");
    }

    @GetMapping("/abc")
    public ModelAndView getOrderInfoByScanQRCode() {
        return null;
    }

    @GetMapping("/print-invoice/{id}")
    public void exportToPDF(@PathVariable("id") Long orderId, HttpServletResponse response) {
        try {
            Optional<OrderDTO> orderDTO = mvOrderService.findById(orderId);
            if (orderDTO.isEmpty()) {
                throw new BadRequestException();
            }
            mvPrintInvoiceService.printInvoicePDF(orderId, null, true, response);
        } catch (RuntimeException ex) {
            throw new AppException(ex);
        }
    }

    @PostMapping("/{orderId}/item/add")
    @PreAuthorize("@vldModuleSales.updateOrder(true)")
    public ModelAndView addItem(@PathVariable("orderId") long orderId, @RequestParam("productVariantSelectedId") String[] productVariantSelectedId) {
        Optional<OrderDTO> orderOpt = mvOrderService.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new ResourceNotFoundException("Đơn hàng không tồn tại!");
        }
        if (!mvOrderStatusCanModifyItem.contains(orderOpt.get().getTrangThaiDonHang().getCode())) {
            throw new BadRequestException("Đơn hàng đang ở trạng thái không cho phép chỉnh sửa!");
        }
        if (productVariantSelectedId == null || productVariantSelectedId.length == 0) {
            throw new BadRequestException("Vui lòng chọn sản phẩm!");
        }
        mvOrderItemsService.save(orderOpt.get(), Arrays.stream(productVariantSelectedId).toList());
        return new ModelAndView("redirect:/order/" + orderId);
    }

    @PostMapping("/{orderId}/item/update/{itemId}")
    @PreAuthorize("@vldModuleSales.updateOrder(true)")
    public ModelAndView updateItem(@PathVariable("orderId") long orderId, @PathVariable("itemId") long itemId, @ModelAttribute("items") OrderDetail item) {
        Optional<OrderDTO> orderOpt = mvOrderService.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new ResourceNotFoundException("Đơn hàng không tồn tại!");
        }
        if (!mvOrderStatusCanModifyItem.contains(orderOpt.get().getTrangThaiDonHang().getCode())) {
            throw new BadRequestException("Đơn hàng đang ở trạng thái không cho phép chỉnh sửa!");
        }
        if (item.getQuantity() == 0) {
            throw new BadRequestException("Số lượng không được nhỏ hơn 1!");
        }
        mvOrderItemsService.update(item, itemId);
        return new ModelAndView("redirect:/order/" + orderId);
    }

    @PostMapping("/{orderId}/item/delete/{itemId}")
    @PreAuthorize("@vldModuleSales.updateOrder(true)")
    public ModelAndView deleteItem(@PathVariable("orderId") long orderId, @PathVariable("itemId") long itemId) {
        Optional<OrderDTO> orderOpt = mvOrderService.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new ResourceNotFoundException("Đơn hàng không tồn tại!");
        }
        if (!mvOrderStatusCanDeleteItem.contains(orderOpt.get().getTrangThaiDonHang().getCode())) {
            throw new BadRequestException("Đơn hàng đang ở trạng thái không cho phép xóa!");
        }
        mvOrderItemsService.delete(itemId);
        return new ModelAndView("redirect:/order/" + orderId);
    }
}