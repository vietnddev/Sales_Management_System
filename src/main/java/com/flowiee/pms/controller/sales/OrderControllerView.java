package com.flowiee.pms.controller.sales;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flowiee.pms.entity.sales.Items;
import com.flowiee.pms.entity.sales.Order;
import com.flowiee.pms.entity.sales.OrderCart;
import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.service.sales.*;
import com.flowiee.pms.service.system.AccountService;
import com.flowiee.pms.utils.*;
import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/don-hang")
public class OrderControllerView extends BaseController {
    private final OrderService orderService;
    private final OrderExportService orderExportService;
    private final CategoryService categoryService;
    private final CustomerService customerService;
    private final CartService cartService;
    private final CartItemsService cartItemsService;
    private final VoucherTicketService voucherTicketService;
    private final AccountService accountService;

    @Autowired
    public OrderControllerView(OrderService orderService, OrderExportService orderExportService, CategoryService categoryService,
                               CustomerService customerService, CartService cartService, CartItemsService cartItemsService,
                               VoucherTicketService voucherTicketService, AccountService accountService) {
        this.orderService = orderService;
        this.orderExportService = orderExportService;
        this.categoryService = categoryService;
        this.customerService = customerService;
        this.cartService = cartService;
        this.cartItemsService = cartItemsService;
        this.voucherTicketService = voucherTicketService;
        this.accountService = accountService;
    }

    @GetMapping
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public ModelAndView viewAllOrders() {
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_ORDER);
        modelAndView.addObject("listKenhBanHang", categoryService.findSubCategory(AppConstants.CATEGORY.SALES_CHANNEL.getName(), null));
        modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(AppConstants.CATEGORY.PAYMENT_METHOD.getName(), null));
        modelAndView.addObject("listKhachHang", customerService.findAll(-1, -1, null, null, null, null, null, null).getContent());
        modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
        modelAndView.addObject("listTrangThaiDonHang", categoryService.findSubCategory(AppConstants.CATEGORY.ORDER_STATUS.getName(), null));
        return baseView(modelAndView);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleSales.readOrder(true)")
    public ModelAndView findDonHangDetail(@PathVariable("id") Integer orderId) throws JsonProcessingException {
        Optional<OrderDTO> orderDetail = orderService.findById(orderId);
        if (orderId <= 0 || orderDetail.isEmpty()) {
            throw new NotFoundException("Order not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_ORDER_DETAIL);
        modelAndView.addObject("orderDetailId", orderId);
        modelAndView.addObject("orderDetail", orderDetail.get());
        modelAndView.addObject("listOrderDetail", orderDetail.get().getListOrderDetailDTO());
        modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(AppConstants.CATEGORY.PAYMENT_METHOD.getName(), null));
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

        double totalAmountWithoutDiscount = cartService.calTotalAmountWithoutDiscount(listOrderCart.get(0).getId());
        double amountDiscount = 0;
        double totalAmountDiscount = totalAmountWithoutDiscount - amountDiscount;
        modelAndView.addObject("totalAmountWithoutDiscount", totalAmountWithoutDiscount);
        //modelAndView.addObject("amountDiscount", amountDiscount);
        modelAndView.addObject("totalAmountDiscount", totalAmountDiscount);
        return baseView(modelAndView);
    }

    @PostMapping("/ban-hang/cart/item/add")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public ModelAndView addItemsToCart(@RequestParam("cartId") Integer cartId, @RequestParam("bienTheSanPhamId") String[] bienTheSanPhamId) {
        if (cartId <= 0 || cartService.findById(cartId).isEmpty()) {
            throw new NotFoundException("Cart not found!");
        }
        List<String> listProductVariantId = Arrays.stream(bienTheSanPhamId).toList();
        for (String productVariantId : listProductVariantId) {
            if (cartService.isItemExistsInCart(cartId, Integer.parseInt(productVariantId))) {
                Items items = cartItemsService.findItemByCartAndProductVariant(cartId, Integer.parseInt(productVariantId));
                cartItemsService.increaseItemQtyInCart(items.getId(), items.getQuantity() + 1);
            } else {
                Items items = new Items();
                items.setOrderCart(new OrderCart(cartId));
                items.setProductDetail(new ProductDetail(Integer.parseInt(productVariantId)));
                items.setQuantity(1);
                items.setNote("");
                cartItemsService.save(items);
            }
        }
        return new ModelAndView("redirect:/don-hang/ban-hang");
    }

    @PostMapping("/ban-hang/cart/item/update/{itemId}")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public ModelAndView updateItemsOfCart(@RequestParam("cartId") Integer cartId,
                                          @ModelAttribute("items") Items items,
                                          @PathVariable("itemId") Integer itemId) {
        if (cartId <= 0 || cartService.findById(cartId).isEmpty()) {
            throw new NotFoundException("Cart not found!");
        }
        items.setId(itemId);
        items.setOrderCart(cartService.findById(cartId).get());
        if (items.getQuantity() > 0) {
            cartItemsService.save(items);
        } else {
            cartItemsService.delete(items.getId());
        }
        return new ModelAndView("redirect:/don-hang/ban-hang");
    }

    @PostMapping("/ban-hang/cart/item/delete/{itemId}")
    @PreAuthorize("@vldModuleSales.insertOrder(true)")
    public ModelAndView deleteItemsOfCart(@RequestParam("cartId") Integer cartId, @PathVariable("itemId") Integer itemId) {
        if (cartService.findById(cartId).isEmpty()) {
            throw new BadRequestException("Sản phẩm cần xóa trong giỏ hàng không tồn tại! cartId=" + cartId + ", itemId=" + itemId);
        }
        cartItemsService.delete(itemId);
        return new ModelAndView("redirect:/don-hang/ban-hang");
    }
    
    @PostMapping("/ban-hang/cart/add-voucher/{code}")
    @PreAuthorize("@vldModuleSales.readVoucher(true)")
    public ModelAndView checkToUse(@PathVariable("code") String code) {
        ModelAndView modelAndView = new ModelAndView("redirect:/don-hang/ban-hang");
        modelAndView.addObject("ticket_code", code);
        modelAndView.addObject("ticket_status", voucherTicketService.checkTicketToUse(code));
        modelAndView.addObject("ticket_info", voucherTicketService.findTicketByCode(code));
        return modelAndView;
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("@vldModuleSales.updateOrder(true)")
    public ModelAndView update(@ModelAttribute("donHang") OrderDTO order, @PathVariable("id") Integer orderId) {
        orderService.update(order, orderId);
        return new ModelAndView("redirect:/don-hang");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("@vldModuleSales.deleteOrder(true)")
    public ModelAndView delete(@PathVariable("id") Integer orderId) {
        orderService.delete(orderId);
        return new ModelAndView("redirect:/don-hang");
    }

    @GetMapping("/abc")
    public ModelAndView getOrderInfoByScanQRCode() {
        return null;
    }

    @GetMapping("/export/pdf/{id}")
    public void exportToPDF(@PathVariable("id") Integer orderId, HttpServletResponse response) throws IOException {
        try {
            Optional<OrderDTO> orderDTO = orderService.findById(orderId);
            if (orderDTO.isEmpty()) {
                throw new BadRequestException();
            }
            orderExportService.exportToPDF(orderId, null, true, response);
        } catch (RuntimeException ex) {
            throw new AppException(ex);
        }
    }
}