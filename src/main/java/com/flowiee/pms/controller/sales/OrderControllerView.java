package com.flowiee.pms.controller.sales;

import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.entity.sales.OrderCart;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.*;
import com.flowiee.pms.service.system.AccountService;
import com.flowiee.pms.utils.*;
import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.exception.NotFoundException;

import com.flowiee.pms.utils.constants.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderControllerView extends BaseController {
    private final OrderService orderService;
    private final OrderExportService orderExportService;
    private final CategoryService categoryService;
    private final CartService cartService;
    private final VoucherTicketService voucherTicketService;
    private final AccountService accountService;
    private final ProductVariantService productVariantService;

    @Autowired
    public OrderControllerView(OrderService orderService, OrderExportService orderExportService, CategoryService categoryService,
                               CartService cartService, VoucherTicketService voucherTicketService, AccountService accountService,
                               ProductVariantService productVariantService) {
        this.orderService = orderService;
        this.orderExportService = orderExportService;
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.voucherTicketService = voucherTicketService;
        this.accountService = accountService;
        this.productVariantService = productVariantService;
    }

    @GetMapping
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public ModelAndView viewAllOrders() {
        return baseView(new ModelAndView(PagesUtils.PRO_ORDER));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public ModelAndView findDonHangDetail(@PathVariable("id") Integer orderId) {
        Optional<OrderDTO> orderDetail = orderService.findById(orderId);
        if (orderId <= 0 || orderDetail.isEmpty()) {
            throw new NotFoundException("Order not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_ORDER_DETAIL);
        modelAndView.addObject("orderDetailId", orderId);
        modelAndView.addObject("orderDetail", orderDetail.get());
        modelAndView.addObject("listOrderDetail", orderDetail.get().getListOrderDetailDTO());
        modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(CategoryType.PAYMENT_METHOD.getName(), null));
        modelAndView.addObject("donHang", new Order());
        //modelAndView.addObject("donHangThanhToan", new OrderPay());
        return baseView(modelAndView);
    }

    @GetMapping("/ban-hang")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public ModelAndView showPageBanHang() {
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_ORDER_SELL);
        List<OrderCart> orderCartCurrent = cartService.findCartByAccountId(CommonUtils.getUserPrincipal().getId());
        if (orderCartCurrent.isEmpty()) {
            OrderCart orderCart = new OrderCart();
            orderCart.setCreatedBy(CommonUtils.getUserPrincipal().getId());
            cartService.save(orderCart);
        }

        List<OrderCart> listOrderCart = cartService.findCartByAccountId(CommonUtils.getUserPrincipal().getId());
        modelAndView.addObject("listCart", listOrderCart);
        modelAndView.addObject("listAccount", accountService.findAll());
        modelAndView.addObject("listSalesChannel", categoryService.findSalesChannels());
        modelAndView.addObject("listPaymentMethod", categoryService.findPaymentMethods());
        modelAndView.addObject("listOrderStatus", categoryService.findOrderStatus());
        modelAndView.addObject("listProductVariant", productVariantService.findAll());

        double totalAmountWithoutDiscount = cartService.calTotalAmountWithoutDiscount(listOrderCart.get(0).getId());
        double amountDiscount = 0;
        double totalAmountDiscount = totalAmountWithoutDiscount - amountDiscount;
        modelAndView.addObject("totalAmountWithoutDiscount", totalAmountWithoutDiscount);
        //modelAndView.addObject("amountDiscount", amountDiscount);
        modelAndView.addObject("totalAmountDiscount", totalAmountDiscount);
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

    @PostMapping("/update/{id}")
    @PreAuthorize("@vldModuleSales.updateOrder(true)")
    public ModelAndView update(@ModelAttribute("donHang") OrderDTO order, @PathVariable("id") Integer orderId) {
        orderService.update(order, orderId);
        return new ModelAndView("redirect:/order");
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
            orderExportService.printInvoicePDF(orderId, null, true, response);
        } catch (RuntimeException ex) {
            throw new AppException(ex);
        }
    }
}