package com.flowiee.pms.controller.sales;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.entity.sales.OrderDetail;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.*;
import com.flowiee.pms.service.system.AccountService;
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
    OrderService             orderService;
    CategoryService          categoryService;
    OrderItemsService        orderItemsService;
    VoucherTicketService     voucherTicketService;
    OrderPrintInvoiceService printInvoiceService;

    List<String> mvOrderStatusCanModify = List.of("PRP", "W4D");
    List<String> mvOrderStatusCanDelete = List.of("PRP");

    @GetMapping
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public ModelAndView viewAllOrders() {
        setupSearchTool(true, List.of("BRANCH", CategoryType.GROUP_CUSTOMER, CategoryType.PAYMENT_METHOD, CategoryType.ORDER_STATUS, CategoryType.SALES_CHANNEL));
        return baseView(new ModelAndView(Pages.PRO_ORDER.getTemplate()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public ModelAndView findDonHangDetail(@PathVariable("id") Integer orderId) {
        Optional<OrderDTO> orderDetail = orderService.findById(orderId);
        if (orderId <= 0 || orderDetail.isEmpty()) {
            throw new ResourceNotFoundException("Order not found!");
        }
        List<Category> orderStatus = new ArrayList<>(List.of(new Category(orderDetail.get().getOrderStatusId(), orderDetail.get().getOrderStatusName())));
        orderStatus.addAll(categoryService.findOrderStatus(orderDetail.get().getOrderStatusId()));

        ModelAndView modelAndView = new ModelAndView(Pages.PRO_ORDER_DETAIL.getTemplate());
        modelAndView.addObject("orderDetailId", orderId);
        modelAndView.addObject("orderDetail", orderDetail.get());
        modelAndView.addObject("listOrderDetail", orderDetail.get().getListOrderDetailDTO());
        modelAndView.addObject("listPaymentMethod", categoryService.findSubCategory(CategoryType.PAYMENT_METHOD, null, null, -1, -1).getContent());
        modelAndView.addObject("orderStatus", orderStatus);
        modelAndView.addObject("allowEdit", mvOrderStatusCanModify.contains(orderDetail.get().getTrangThaiDonHang().getCode()));

        return baseView(modelAndView);
    }
    
    @PostMapping("/ban-hang/cart/add-voucher/{code}")
    @PreAuthorize("@vldModuleSales.readVoucher(true)")
    public ModelAndView checkToUse(@PathVariable("code") String code) {
        ModelAndView modelAndView = new ModelAndView("redirect:/order/ban-hang");
        modelAndView.addObject("ticket_code", code);
        modelAndView.addObject("ticket_status", voucherTicketService.checkTicketToUse(code));
        modelAndView.addObject("ticket_info", voucherTicketService.findTicketByCode(code));
        return modelAndView;
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("@vldModuleSales.deleteOrder(true)")
    public ModelAndView delete(@PathVariable("id") Integer orderId) {
        orderService.delete(orderId);
        return new ModelAndView("redirect:/order");
    }

    @GetMapping("/abc")
    public ModelAndView getOrderInfoByScanQRCode() {
        return null;
    }

    @GetMapping("/print-invoice/{id}")
    public void exportToPDF(@PathVariable("id") Integer orderId, HttpServletResponse response) {
        try {
            Optional<OrderDTO> orderDTO = orderService.findById(orderId);
            if (orderDTO.isEmpty()) {
                throw new BadRequestException();
            }
            printInvoiceService.printInvoicePDF(orderId, null, true, response);
        } catch (RuntimeException ex) {
            throw new AppException(ex);
        }
    }

    @PostMapping("/{orderId}/item/add")
    @PreAuthorize("@vldModuleSales.updateOrder(true)")
    public ModelAndView addItem(@PathVariable("orderId") int orderId, @RequestParam("productVariantSelectedId") String[] productVariantSelectedId) {
        Optional<OrderDTO> orderOpt = orderService.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new ResourceNotFoundException("Đơn hàng không tồn tại!");
        }
        if (!mvOrderStatusCanModify.contains(orderOpt.get().getTrangThaiDonHang().getCode())) {
            throw new BadRequestException("Đơn hàng đang ở trạng thái không cho phép chỉnh sửa!");
        }
        if (productVariantSelectedId == null || productVariantSelectedId.length == 0) {
            throw new BadRequestException("Vui lòng chọn sản phẩm!");
        }
        orderItemsService.save(orderOpt.get(), Arrays.stream(productVariantSelectedId).toList());
        return new ModelAndView("redirect:/order/" + orderId);
    }

    @PostMapping("/{orderId}/item/update/{itemId}")
    @PreAuthorize("@vldModuleSales.updateOrder(true)")
    public ModelAndView updateItem(@PathVariable("orderId") int orderId, @PathVariable("itemId") int itemId, @ModelAttribute("items") OrderDetail item) {
        Optional<OrderDTO> orderOpt = orderService.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new ResourceNotFoundException("Đơn hàng không tồn tại!");
        }
        if (!mvOrderStatusCanModify.contains(orderOpt.get().getTrangThaiDonHang().getCode())) {
            throw new BadRequestException("Đơn hàng đang ở trạng thái không cho phép chỉnh sửa!");
        }
        if (item.getQuantity() == 0) {
            throw new BadRequestException("Số lượng không được nhỏ hơn 1!");
        }
        orderItemsService.update(item, itemId);
        return new ModelAndView("redirect:/order/" + orderId);
    }

    @PostMapping("/{orderId}/item/delete/{itemId}")
    @PreAuthorize("@vldModuleSales.updateOrder(true)")
    public ModelAndView deleteItem(@PathVariable("orderId") int orderId, @PathVariable("itemId") int itemId) {
        Optional<OrderDTO> orderOpt = orderService.findById(orderId);
        if (orderOpt.isEmpty()) {
            throw new ResourceNotFoundException("Đơn hàng không tồn tại!");
        }
        if (!mvOrderStatusCanDelete.contains(orderOpt.get().getTrangThaiDonHang().getCode())) {
            throw new BadRequestException("Đơn hàng đang ở trạng thái không cho phép xóa!");
        }
        orderItemsService.delete(itemId);
        return new ModelAndView("redirect:/order/" + orderId);
    }
}