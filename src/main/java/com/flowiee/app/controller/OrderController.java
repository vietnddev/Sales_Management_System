package com.flowiee.app.controller;

import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.entity.*;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.EndPointUtil;
import com.flowiee.app.utils.PagesUtil;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.service.CategoryService;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.service.*;
import com.flowiee.app.model.request.OrderRequest;
import com.flowiee.app.security.ValidateModuleProduct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(EndPointUtil.PRO_ORDER)
public class OrderController extends BaseController {
    @Autowired private OrderService orderService;
    @Autowired private ProductVariantService productVariantService;
    @Autowired private CategoryService categoryService;
    @Autowired private CustomerService customerService;
    @Autowired private OrderPayService orderPayService;
    @Autowired private CartService cartService;
    @Autowired private ItemsService itemsService;
    @Autowired private VoucherTicketService voucherTicketService;
    @Autowired private ValidateModuleProduct validateModuleProduct;

    @GetMapping
    public ModelAndView viewAllOrders() {
        validateModuleProduct.readOrder(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_ORDER);
        modelAndView.addObject("listOrder", orderService.findAllOrder());
        modelAndView.addObject("listBienTheSanPham", productVariantService.findAll());
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
        modelAndView.addObject("listBienTheSanPham", productVariantService.findAll());
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
    public ModelAndView findDonHangDetail(@PathVariable("id") Integer id) {
        validateModuleProduct.readOrder(true);
        if (id <= 0 || orderService.findOrderById(id) == null) {
            throw new NotFoundException("Order not found!");
        }
        OrderDTO orderDetail = orderService.findOrderById(id);
        List<OrderDetail> listOrderDetail = orderService.findOrderDetailsByOrderId(id);
        int totalProduct = 0;
        for (OrderDetail od : listOrderDetail) {
            totalProduct += od.getSoLuong();
        }
        orderDetail.setTotalProduct(totalProduct);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_ORDER_DETAIL);
        modelAndView.addObject("orderDetail", orderDetail);
        modelAndView.addObject("listOrderDetail", listOrderDetail);
        modelAndView.addObject("listThanhToan", orderPayService.findByOrder(id));
        modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(AppConstants.CATEGORY.PAYMENT_METHOD.getName()));
        modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
        modelAndView.addObject("donHang", new Order());
        modelAndView.addObject("donHangThanhToan", new OrderPay());
        return baseView(modelAndView);
    }

    @GetMapping("/ban-hang")
    public ModelAndView showPageBanHang() {
        validateModuleProduct.insertOrder(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_ORDER_SELL);
        List<OrderCart> orderCartCurrent = cartService.findByAccountId(CommonUtil.getCurrentAccountId());
        if (orderCartCurrent.isEmpty()) {
            OrderCart orderCart = new OrderCart();
            orderCart.setCreatedBy(CommonUtil.getCurrentAccountId());
            cartService.save(orderCart);
        }
        modelAndView.addObject("listDonHang", orderService.findAllOrder());
        modelAndView.addObject("listBienTheSanPham", productVariantService.findAll());
        modelAndView.addObject("listKenhBanHang", categoryService.findSubCategory(AppConstants.CATEGORY.SALES_CHANNEL.getName()));
        modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(AppConstants.CATEGORY.PAYMENT_METHOD.getName()));
        modelAndView.addObject("listKhachHang", customerService.findAllCustomer());
        modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
        modelAndView.addObject("listTrangThaiDonHang", categoryService.findSubCategory(AppConstants.CATEGORY.ORDER_STATUS.getName()));

        List<OrderCart> listOrderCart = cartService.findByAccountId(CommonUtil.getCurrentAccountId());
        modelAndView.addObject("listCart", listOrderCart);

        modelAndView.addObject("donHangRequest", new OrderRequest());
        modelAndView.addObject("donHang", new Order());
        modelAndView.addObject("khachHang", new Customer());
        modelAndView.addObject("cart", new OrderCart());
        modelAndView.addObject("items", new Items());
        return baseView(modelAndView);
    }

    @PostMapping("/ban-hang/cart/{id}/add-items")
    public ModelAndView addItemsToCart(@PathVariable("id") Integer idCart, HttpServletRequest request) {
        validateModuleProduct.insertOrder(true);
        if (idCart <= 0 || cartService.findById(idCart) == null) {
            throw new NotFoundException("Cart not found!");
        }
        List<String> listProductVariantId = Arrays.stream(request.getParameterValues("bienTheSanPhamId")).toList();
        for (String productVariantId : listProductVariantId) {
            Items items = new Items();
            items.setOrderCart(cartService.findById(idCart));
            items.setProductVariant(productVariantService.findById(Integer.parseInt(productVariantId)));
            items.setSoLuong(1);
            items.setGhiChu("");
            itemsService.save(items);
        }
        return new ModelAndView("redirect:/don-hang/ban-hang");
    }

    @PostMapping("/ban-hang/cart/update/{id}")
    public ModelAndView updateItemsOfCart(@PathVariable("id") Integer id, @ModelAttribute("items") Items items) {
        validateModuleProduct.insertOrder(true);
        if (id <= 0 || cartService.findById(id) == null) {
            throw new NotFoundException("Cart not found!");
        }
        items.setOrderCart(cartService.findById(id));
        if (items.getSoLuong() > 0) {
            itemsService.save(items);
        } else {
            itemsService.delete(items.getId());
        }
        return new ModelAndView("redirect:/don-hang/ban-hang");
    }

    @PostMapping("/ban-hang/cart/delete/{id}")
    public ModelAndView deleteItemsOfCart(@PathVariable("id") Integer id) {
        validateModuleProduct.insertOrder(true);
        itemsService.findByCartId(id).forEach(items -> {
            itemsService.delete(items.getId());
        });
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
    public ModelAndView insert(@ModelAttribute("orderRequest") OrderRequest orderRequest, HttpServletRequest request) {
        validateModuleProduct.insertOrder(true);
        String thoiGianDatHangString = request.getParameter("thoiGianDatHang");
        if (thoiGianDatHangString != null) {
            orderRequest.setThoiGianDatHang(CommonUtil.convertStringToDate(thoiGianDatHangString));
        } else {
            orderRequest.setThoiGianDatHang(new Date());
        }
        List<Integer> listProductVariantId = new ArrayList<>();
        List<String> listProductVariantIdRequest = Arrays.stream(request.getParameter("listBienTheSanPhamId").split(",")).toList();
        for (String idString : listProductVariantIdRequest) {
            listProductVariantId.add(Integer.parseInt(idString));
        }
        orderRequest.setListBienTheSanPham(listProductVariantId);
        orderRequest.setTrangThaiThanhToan(false);
        orderService.saveOrder(orderRequest);
        //Sau khi đã lưu đơn hàng thì xóa all items
        itemsService.findByCartId(orderRequest.getCartId()).forEach(items -> {
            itemsService.delete(items.getId());
        });
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
    public ModelAndView thanhToan(@PathVariable("id") Integer donHangId, @ModelAttribute("donHangThanhToan") OrderPay orderPay) {
        validateModuleProduct.updateOrder(true);
        orderPay.setMaPhieu("PTT" + donHangId + CommonUtil.now("yyMMddHHmmss"));
        orderPay.setOrder(new Order(orderService.findOrderById(donHangId).getOrderId()));
        orderPay.setPaymentStatus(true);
        orderPayService.save(orderPay);
        return new ModelAndView("redirect:/don-hang/" + donHangId);
    }

    @GetMapping(EndPointUtil.PRO_ORDER_EXPORT)
    public ResponseEntity<?> exportDanhSachDonHang() {
        validateModuleProduct.readOrder(true);
        return orderService.exportDanhSachDonHang();
    }
}