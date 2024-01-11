package com.flowiee.app.controller;

import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.entity.*;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.utils.*;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.service.CategoryService;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.service.*;
import com.flowiee.app.model.request.OrderRequest;
import com.flowiee.app.security.ValidateModuleProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(EndPointUtil.PRO_ORDER)
public class OrderController extends BaseController {
    private final OrderService orderService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CustomerService customerService;
    private final CartService cartService;
    private final VoucherTicketService voucherTicketService;
    private final ValidateModuleProduct validateModuleProduct;

    @Autowired
    public OrderController(OrderService orderService, ProductService productService, CategoryService categoryService, CustomerService customerService, CartService cartService, VoucherTicketService voucherTicketService, ValidateModuleProduct validateModuleProduct) {
        this.orderService = orderService;
        this.productService = productService;
        this.categoryService = categoryService;
        this.customerService = customerService;
        this.cartService = cartService;
        this.voucherTicketService = voucherTicketService;
        this.validateModuleProduct = validateModuleProduct;
    }

    @GetMapping
    public ModelAndView viewAllOrders() {
        validateModuleProduct.readOrder(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_ORDER);
        modelAndView.addObject("listOrder", orderService.findAllOrder());
        modelAndView.addObject("listBienTheSanPham", productService.findAllProductVariants());
        modelAndView.addObject("listKenhBanHang", categoryService.findSubCategory(AppConstants.CATEGORY.SALES_CHANNEL.getName()));
        modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(AppConstants.CATEGORY.PAYMENT_METHOD.getName()));
        modelAndView.addObject("listKhachHang", customerService.findAllCustomer());
        modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
        modelAndView.addObject("listTrangThaiDonHang", categoryService.findSubCategory(AppConstants.CATEGORY.ORDER_STATUS.getName()));
        modelAndView.addObject("donHangRequest", new OrderRequest());
        modelAndView.addObject("donHang", new Order());
        return baseView(modelAndView);
    }

    @PostMapping
    public ModelAndView filterListDonHang(@ModelAttribute("donHangRequest") OrderRequest request) {
        validateModuleProduct.readOrder(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_ORDER);
        modelAndView.addObject("listDonHang", orderService.findAllOrder());
        modelAndView.addObject("listBienTheSanPham", productService.findAllProductVariants());
        modelAndView.addObject("listKhachHang", customerService.findAllCustomer());
        modelAndView.addObject("listNhanVienBanHang", accountService.findAll());

        modelAndView.addObject("selected_kenhBanHang", request.getKenhBanHang() == 0 ? null : categoryService.findById(request.getKenhBanHang()));
        modelAndView.addObject("listKenhBanHang", categoryService.findSubCategory(AppConstants.CATEGORY.SALES_CHANNEL.getName()));

        modelAndView.addObject("selected_hinhThucThanhToan", request.getHinhThucThanhToan() == 0 ? null : categoryService.findById(request.getHinhThucThanhToan()));
        modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(AppConstants.CATEGORY.PAYMENT_METHOD.getName()));

        modelAndView.addObject("selected_trangThaiDonHang", request.getTrangThaiDonHang() == 0 ? null : categoryService.findById(request.getTrangThaiDonHang()));
        modelAndView.addObject("listTrangThaiDonHang", categoryService.findSubCategory(AppConstants.CATEGORY.ORDER_STATUS.getName()));

        modelAndView.addObject("donHangRequest", new OrderRequest());
        modelAndView.addObject("donHang", new Order());
        return baseView(modelAndView);
    }

    @GetMapping("/{id}")
    public ModelAndView findDonHangDetail(@PathVariable("id") Integer orderId) {
        validateModuleProduct.readOrder(true);
        if (orderId <= 0 || orderService.findOrderById(orderId) == null) {
            throw new NotFoundException("Order not found!");
        }
        OrderDTO orderDetail = orderService.findOrderById(orderId);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_ORDER_DETAIL);
        modelAndView.addObject("orderDetail", orderDetail);
        modelAndView.addObject("listOrderDetail", orderDetail.getListOrderDetail());
        //modelAndView.addObject("listThanhToan", orderPayService.findByOrder(id));
        modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(AppConstants.CATEGORY.PAYMENT_METHOD.getName()));
        modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
        modelAndView.addObject("donHang", new Order());
        //modelAndView.addObject("donHangThanhToan", new OrderPay());
        return baseView(modelAndView);
    }

    @GetMapping("/ban-hang")
    public ModelAndView showPageBanHang() {
        validateModuleProduct.insertOrder(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_ORDER_SELL);
        List<OrderCart> orderCartCurrent = cartService.findCartByAccountId(CommonUtil.getCurrentAccountId());
        if (orderCartCurrent.isEmpty()) {
            OrderCart orderCart = new OrderCart();
            orderCart.setCreatedBy(CommonUtil.getCurrentAccountId());
            cartService.saveCart(orderCart);
        }
        modelAndView.addObject("listDonHang", orderService.findAllOrder());
        modelAndView.addObject("listBienTheSanPham", productService.findAllProductVariants());
        modelAndView.addObject("listKenhBanHang", categoryService.findSubCategory(AppConstants.CATEGORY.SALES_CHANNEL.getName()));
        modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(AppConstants.CATEGORY.PAYMENT_METHOD.getName()));
        modelAndView.addObject("listKhachHang", customerService.findAllCustomer());
        modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
        modelAndView.addObject("listTrangThaiDonHang", categoryService.findSubCategory(AppConstants.CATEGORY.ORDER_STATUS.getName()));

        List<OrderCart> listOrderCart = cartService.findCartByAccountId(CommonUtil.getCurrentAccountId());
        modelAndView.addObject("listCart", listOrderCart);

        double totalAmountWithoutDiscount = cartService.calTotalAmountWithoutDiscount(listOrderCart.get(0).getId());
        double amountDiscount = 0;
        double totalAmountDiscount = totalAmountWithoutDiscount - amountDiscount;
        modelAndView.addObject("totalAmountWithoutDiscount", totalAmountWithoutDiscount);
        modelAndView.addObject("amountDiscount", amountDiscount);
        modelAndView.addObject("totalAmountDiscount", totalAmountDiscount);
        modelAndView.addObject("donHangRequest", new OrderRequest());
        modelAndView.addObject("donHang", new Order());
        modelAndView.addObject("khachHang", new Customer());
        modelAndView.addObject("cart", new OrderCart());
        modelAndView.addObject("items", new Items());
        return baseView(modelAndView);
    }

    @PostMapping("/ban-hang/cart/item/add")
    public ModelAndView addItemsToCart(@RequestParam("cartId") Integer cartId, @RequestParam("bienTheSanPhamId") String[] bienTheSanPhamId) {
        validateModuleProduct.insertOrder(true);
        if (cartId <= 0 || cartService.findCartById(cartId) == null) {
            throw new NotFoundException("Cart not found!");
        }
        List<String> listProductVariantId = Arrays.stream(bienTheSanPhamId).toList();
        for (String productVariantId : listProductVariantId) {
            if (cartService.isItemExistsInCart(cartId, Integer.parseInt(productVariantId))) {
                Items items = cartService.findItemByCartAndProductVariant(cartId, Integer.parseInt(productVariantId));
                cartService.increaseItemQtyInCart(items.getId(), items.getSoLuong() + 1);
            } else {
                Items items = new Items();
                items.setOrderCart(new OrderCart(cartId));
                items.setProductVariant(new ProductVariant(Integer.parseInt(productVariantId)));
                items.setSoLuong(1);
                items.setGhiChu("");
                cartService.saveItem(items);
            }
        }
        return new ModelAndView("redirect:/don-hang/ban-hang");
    }

    @PostMapping("/ban-hang/cart/item/update/{itemId}")
    public ModelAndView updateItemsOfCart(@RequestParam("cartId") Integer cartId,
                                          @ModelAttribute("items") Items items,
                                          @PathVariable("itemId") Integer itemId) {
        validateModuleProduct.insertOrder(true);
        if (cartId <= 0 || cartService.findCartById(cartId) == null) {
            throw new NotFoundException("Cart not found!");
        }
        items.setId(itemId);
        items.setOrderCart(cartService.findCartById(cartId));
        if (items.getSoLuong() > 0) {
            cartService.saveItem(items);
        } else {
            cartService.deleteItem(items.getId());
        }
        return new ModelAndView("redirect:/don-hang/ban-hang");
    }

    @PostMapping("/ban-hang/cart/item/delete/{itemId}")
    public ModelAndView deleteItemsOfCart(@RequestParam("cartId") Integer cartId, @PathVariable("itemId") Integer itemId) {
        validateModuleProduct.insertOrder(true);
        if (cartService.findCartById(cartId) == null || cartService.findItemById(itemId) == null) {
            throw new BadRequestException("Sản phẩm cần xóa trong giỏ hàng không tồn tại! cartId=" + cartId + ", itemId=" + itemId);
        }
        cartService.deleteItem(itemId);
        return new ModelAndView("redirect:/don-hang/ban-hang");
    }
    
    @PostMapping("/ban-hang/cart/add-voucher/{code}")
    public ModelAndView checkToUse(@PathVariable("code") String code) {
        validateModuleProduct.readVoucher(true);
        ModelAndView modelAndView = new ModelAndView("redirect:/don-hang/ban-hang");
        modelAndView.addObject("ticket_code", code);
        modelAndView.addObject("ticket_status", voucherTicketService.checkTicketToUse(code));
        modelAndView.addObject("ticket_info", voucherTicketService.findByCode(code));
        return modelAndView;
    }

    @PostMapping("/insert")
    public ModelAndView insert(@ModelAttribute("orderRequest") OrderRequest orderRequest,
                               @RequestParam("thoiGianDatHang") @Nullable String orderTimeStr,
                               @RequestParam("listBienTheSanPhamId") String productVariantIds) {
        validateModuleProduct.insertOrder(true);
        if (orderTimeStr != null) {
            orderRequest.setThoiGianDatHang(DateUtils.convertStringToDate(orderTimeStr));
        } else {
            orderRequest.setThoiGianDatHang(new Date());
        }
        orderRequest.setTrangThaiThanhToan(false);
        orderService.saveOrder(orderRequest);
        return new ModelAndView("redirect:/don-hang");
    }

    @PostMapping("/update/{id}")
    public ModelAndView update(@ModelAttribute("donHang") Order order, @PathVariable("id") Integer orderId) {
        validateModuleProduct.updateOrder(true);
        orderService.updateOrder(order, orderId);
        return new ModelAndView("redirect:/don-hang");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer orderId) {
        validateModuleProduct.deleteOrder(true);
        orderService.deleteOrder(orderId);
        return new ModelAndView("redirect:/don-hang");
    }

    @PostMapping("/thanh-toan/{id}")
    public ModelAndView doPay(@PathVariable("id") Integer orderId,
                              @RequestParam("paymentTime") Date paymentTime,
                              @RequestParam("paymentMethod") Integer paymentMethod,
                              @RequestParam("note") @Nullable String note) {
        validateModuleProduct.updateOrder(true);
        if (orderId == null || orderId <= 0 || orderService.findOrderById(orderId) == null) {
            throw new NotFoundException("Đơn hàng cần thanh toán không tồn tại! orderId=" + orderId);
        }
        if (paymentTime == null) {
            paymentTime = new Date();
        }
        if (paymentMethod == null || paymentMethod <= 0) {
            throw new BadRequestException("Hình thức thanh toán không hợp lệ!");
        }
        orderService.doPay(orderId, paymentTime, paymentMethod, note);
        return new ModelAndView("redirect:/don-hang/" + orderId);
    }

    @GetMapping(EndPointUtil.PRO_ORDER_EXPORT)
    public ResponseEntity<?> exportDanhSachDonHang() {
        validateModuleProduct.readOrder(true);
        return orderService.exportDanhSachDonHang();
    }

    @GetMapping("/abc")
    public ModelAndView getOrderInfoByScanQRCode() {
        return null;
    }
}