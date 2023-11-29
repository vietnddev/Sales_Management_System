package com.flowiee.app.controller;

import com.flowiee.app.dto.OrderDTO;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.FlowieeUtil;
import com.flowiee.app.utils.PagesUtil;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.service.CategoryService;
import com.flowiee.app.security.author.ValidateModuleProduct;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.service.*;
import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.Items;
import com.flowiee.app.entity.Order;
import com.flowiee.app.entity.OrderCart;
import com.flowiee.app.entity.OrderPay;
import com.flowiee.app.model.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/don-hang")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService donHangChiTietService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderPayService orderPayService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private ValidateModuleProduct validateModuleProduct;

    @GetMapping
    public ModelAndView viewAllOrders() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleProduct.readOrder()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_ORDER);
        modelAndView.addObject("listOrder", orderService.findAll(new OrderDTO()));
        modelAndView.addObject("listBienTheSanPham", productVariantService.findAll());
        modelAndView.addObject("listKenhBanHang", categoryService.findSubCategory(AppConstants.SALESCHANNEL));
        modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(AppConstants.PAYMETHOD));
        modelAndView.addObject("listKhachHang", customerService.findAll());
        modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
        modelAndView.addObject("listTrangThaiDonHang", categoryService.findSubCategory(AppConstants.ORDERSTATUS));
        modelAndView.addObject("donHangRequest", new OrderRequest());
        modelAndView.addObject("donHang", new Order());
        return baseView(modelAndView);
    }

    @PostMapping
    public ModelAndView filterListDonHang(@ModelAttribute("donHangRequest") OrderRequest request) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleProduct.readOrder()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_ORDER);
        modelAndView.addObject("listDonHang", orderService.findAll(new OrderDTO()));
        modelAndView.addObject("listBienTheSanPham", productVariantService.findAll());
        modelAndView.addObject("listKhachHang", customerService.findAll());
        modelAndView.addObject("listNhanVienBanHang", accountService.findAll());

        modelAndView.addObject("selected_kenhBanHang", request.getKenhBanHang() == 0 ? null : categoryService.findById(request.getKenhBanHang()));
        modelAndView.addObject("listKenhBanHang", categoryService.findSubCategory(AppConstants.SALESCHANNEL));

        modelAndView.addObject("selected_hinhThucThanhToan", request.getHinhThucThanhToan() == 0 ? null : categoryService.findById(request.getHinhThucThanhToan()));
        modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(AppConstants.PAYMETHOD));

        modelAndView.addObject("selected_trangThaiDonHang", request.getTrangThaiDonHang() == 0 ? null : categoryService.findById(request.getTrangThaiDonHang()));
        modelAndView.addObject("listTrangThaiDonHang", categoryService.findSubCategory(AppConstants.ORDERSTATUS));

        modelAndView.addObject("donHangRequest", new OrderRequest());
        modelAndView.addObject("donHang", new Order());
        return baseView(modelAndView);
    }

    @GetMapping("/{id}")
    public ModelAndView findDonHangDetail(@PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (id <= 0 || orderService.findById(id) == null) {
            throw new NotFoundException("Order not found!");
        }
        if (!validateModuleProduct.readOrder()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_ORDER_DETAIL);
        modelAndView.addObject("donHangDetail", orderService.findById(id));
        modelAndView.addObject("listDonHangDetail", donHangChiTietService.findByDonHangId(id));
        modelAndView.addObject("listThanhToan", orderPayService.findByOrder(id));
        modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(AppConstants.PAYMETHOD));
        modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
        modelAndView.addObject("donHang", new Order());
        modelAndView.addObject("donHangThanhToan", new OrderPay());
        return baseView(modelAndView);
    }

    @GetMapping("/ban-hang")
    public ModelAndView showPageBanHang() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleProduct.insertOrder()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_ORDER_SELL);
        List<OrderCart> orderCartCurrent = cartService.findByAccountId(FlowieeUtil.getCurrentAccountId());
        if (orderCartCurrent.isEmpty()) {
            OrderCart orderCart = new OrderCart();
            orderCart.setCreatedBy(FlowieeUtil.getCurrentAccountId());
            cartService.save(orderCart);
        }
        modelAndView.addObject("listDonHang", orderService.findAll());
        modelAndView.addObject("listBienTheSanPham", productVariantService.findAll());
        modelAndView.addObject("listKenhBanHang", categoryService.findSubCategory(AppConstants.SALESCHANNEL));
        modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(AppConstants.PAYMETHOD));
        modelAndView.addObject("listKhachHang", customerService.findAll());
        modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
        modelAndView.addObject("listTrangThaiDonHang", categoryService.findSubCategory(AppConstants.ORDERSTATUS));

        List<OrderCart> listOrderCart = cartService.findByAccountId(FlowieeUtil.getCurrentAccountId());
        modelAndView.addObject("listCart", listOrderCart);

        modelAndView.addObject("donHangRequest", new OrderRequest());
        modelAndView.addObject("donHang", new Order());
        modelAndView.addObject("khachHang", new Customer());
        modelAndView.addObject("cart", new OrderCart());
        modelAndView.addObject("items", new Items());
        return baseView(modelAndView);
    }

    @PostMapping("/ban-hang/cart/{id}/add-items")
    public String addItemsToCart(@PathVariable("id") Integer idCart, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.insertOrder()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (idCart <= 0 || cartService.findById(idCart) == null) {
            throw new NotFoundException("Cart not found!");
        }
        List<String> listBienTheSanPham = Arrays.stream(request.getParameterValues("bienTheSanPhamId")).toList();
        for (String bTSanPhamId : listBienTheSanPham) {
            Items items = new Items();
            items.setOrderCart(cartService.findById(idCart));
            items.setProductVariant(productVariantService.findById(Integer.parseInt(bTSanPhamId)));
            items.setSoLuong(1);
            items.setGhiChu("");
            itemsService.save(items);
        }
        return "redirect:/don-hang/ban-hang";
    }

    @PostMapping("/ban-hang/cart/update/{id}")
    public String updateItemsOfCart(@PathVariable("id") Integer id, @ModelAttribute("items") Items items) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.insertOrder()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (id <= 0 || cartService.findById(id) == null) {
            throw new NotFoundException("Cart not found!");
        }
        items.setOrderCart(cartService.findById(id));
        if (items.getSoLuong() > 0) {
            itemsService.save(items);
        } else {
            itemsService.delete(items.getId());
        }
        return "redirect:/don-hang/ban-hang";
    }

    @PostMapping("/ban-hang/cart/delete/{id}")
    public String deleteItemsOfCart(@PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.insertOrder()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        itemsService.findByCartId(id).forEach(items -> {
            itemsService.delete(items.getId());
        });
        return "redirect:/don-hang/ban-hang";
    }

    @Transactional
    @PostMapping("/insert")
    public String insert(@ModelAttribute("orderRequest") OrderRequest orderRequest,
                         HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.insertOrder()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        String thoiGianDatHangString = request.getParameter("thoiGianDatHang");
        if (thoiGianDatHangString != null) {
            orderRequest.setThoiGianDatHang(FlowieeUtil.convertStringToDate(thoiGianDatHangString));
        } else {
            orderRequest.setThoiGianDatHang(new Date());
        }
        List<Integer> listProductVariantId = new ArrayList<>();
        List<String> listBtspIdString = Arrays.stream(request.getParameter("listBienTheSanPhamId").split(",")).toList();
        for (String idString : listBtspIdString) {
            listProductVariantId.add(Integer.parseInt(idString));
        }
        orderRequest.setListBienTheSanPham(listProductVariantId);
        orderRequest.setTrangThaiThanhToan(false);
        orderService.save(orderRequest);
        //Sau khi đã lưu đơn hàng thì xóa all items
        itemsService.findByCartId(orderRequest.getCartId()).forEach(items -> {
            itemsService.delete(items.getId());
        });
        return "redirect:/don-hang";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("donHang") Order order, @PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateOrder()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        orderService.update(order, id);
        return "redirect:/don-hang";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.deleteOrder()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        orderService.delete(id);
        return "redirect:/don-hang";
    }

    @PostMapping("/thanh-toan/{id}")
    public String thanhToan(@PathVariable("id") Integer donHangId,
                            @ModelAttribute("donHangThanhToan") OrderPay orderPay) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleProduct.updateOrder()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        orderPay.setMaPhieu("PTT" + donHangId + FlowieeUtil.now("yyMMddHHmmss"));
        orderPay.setOrder(orderService.findById(donHangId));
        orderPay.setPaymentStatus(true);
        orderPayService.save(orderPay);
        return "redirect:/don-hang/" + donHangId;
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportDanhSachDonHang() {
        if (!accountService.isLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.SYS_LOGIN);
        }
        if (validateModuleProduct.readOrder()) {
            return orderService.exportDanhSachDonHang();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.SYS_UNAUTHORIZED);
        }
    }
}