package com.flowiee.app.controller.product;

import com.flowiee.app.common.utils.CategoryUtil;
import com.flowiee.app.common.utils.DateUtil;
import com.flowiee.app.common.utils.FlowieeUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.base.BaseController;
import com.flowiee.app.category.CategoryService;
import com.flowiee.app.category.service.TrangThaiDonHangService;
import com.flowiee.app.service.product.CartService;
import com.flowiee.app.service.product.CustomerService;
import com.flowiee.app.service.product.DonHangThanhToanService;
import com.flowiee.app.service.product.ItemsService;
import com.flowiee.app.service.product.OrderDetailService;
import com.flowiee.app.service.product.OrderService;
import com.flowiee.app.service.product.ProductVariantService;
import com.flowiee.app.service.system.AccountService;
import com.flowiee.app.config.KiemTraQuyenModuleSanPham;
import com.flowiee.app.entity.product.Customer;
import com.flowiee.app.entity.product.Items;
import com.flowiee.app.entity.product.Order;
import com.flowiee.app.entity.product.OrderCart;
import com.flowiee.app.entity.product.OrderPay;
import com.flowiee.app.model.product.OrderRequest;
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
    private AccountService accountService;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TrangThaiDonHangService trangThaiDonHangService;
    @Autowired
    private DonHangThanhToanService donHangThanhToanService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private KiemTraQuyenModuleSanPham kiemTraQuyenModuleSanPham;

    @GetMapping
    public ModelAndView findAllDonHang() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_DONHANG);
            modelAndView.addObject("listDonHang", orderService.findAll());
            modelAndView.addObject("listBienTheSanPham", productVariantService.findAll());
            modelAndView.addObject("listKenhBanHang", categoryService.findSubCategory(CategoryUtil.SALESCHANNEL));
            modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(CategoryUtil.PAYMETHOD));
            modelAndView.addObject("listKhachHang", customerService.findAll());
            modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
            modelAndView.addObject("listTrangThaiDonHang", trangThaiDonHangService.findAll());
            modelAndView.addObject("donHangRequest", new OrderRequest());
            modelAndView.addObject("donHang", new Order());            
            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @PostMapping
    public ModelAndView FilterListDonHang(@ModelAttribute("donHangRequest") OrderRequest request) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_DONHANG);
            modelAndView.addObject("listDonHang", orderService.findAll(request.getSearchTxt(),
                    request.getThoiGianDatHangSearch(),
                    request.getKenhBanHang(),
                    request.getTrangThaiDonHang()));
            modelAndView.addObject("listBienTheSanPham", productVariantService.findAll());
            modelAndView.addObject("listKhachHang", customerService.findAll());
            modelAndView.addObject("listNhanVienBanHang", accountService.findAll());

            modelAndView.addObject("selected_kenhBanHang", request.getKenhBanHang() == 0 ? null : categoryService.findById(request.getKenhBanHang()));
            modelAndView.addObject("listKenhBanHang", categoryService.findSubCategory(CategoryUtil.SALESCHANNEL));

            modelAndView.addObject("selected_hinhThucThanhToan", request.getHinhThucThanhToan() == 0 ? null : categoryService.findById(request.getHinhThucThanhToan()));
            modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(CategoryUtil.PAYMETHOD));

            modelAndView.addObject("selected_trangThaiDonHang", request.getTrangThaiDonHang() == 0 ? null : trangThaiDonHangService.findById(request.getTrangThaiDonHang()));
            modelAndView.addObject("listTrangThaiDonHang", trangThaiDonHangService.findAll());

            modelAndView.addObject("donHangRequest", new OrderRequest());
            modelAndView.addObject("donHang", new Order());
            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ModelAndView findDonHangDetail(@PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_DONHANG_CHITIET);
            modelAndView.addObject("donHangDetail", orderService.findById(id));
            modelAndView.addObject("listDonHangDetail", donHangChiTietService.findByDonHangId(id));
            modelAndView.addObject("listThanhToan", donHangThanhToanService.findByDonHangId(id));
            modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(CategoryUtil.PAYMETHOD));
            modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
            modelAndView.addObject("donHang", new Order());
            modelAndView.addObject("donHangThanhToan", new OrderPay());
            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @GetMapping("/ban-hang")
    public ModelAndView showPageBanHang() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenThemMoiDonHang()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_DONHANG_BANHANG);
            List<OrderCart> orderCartCurrent = cartService.findByAccountId(FlowieeUtil.ACCOUNT_ID);
            if (orderCartCurrent.isEmpty()) {
                OrderCart orderCart = new OrderCart();
                orderCart.setCreatedBy(FlowieeUtil.ACCOUNT_ID + "");
                cartService.save(orderCart);
            }
            modelAndView.addObject("listDonHang", orderService.findAll());
            modelAndView.addObject("listBienTheSanPham", productVariantService.findAll());
            modelAndView.addObject("listKenhBanHang", categoryService.findSubCategory(CategoryUtil.SALESCHANNEL));
            modelAndView.addObject("listHinhThucThanhToan", categoryService.findSubCategory(CategoryUtil.PAYMETHOD));
            modelAndView.addObject("listKhachHang", customerService.findAll());
            modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
            modelAndView.addObject("listTrangThaiDonHang", trangThaiDonHangService.findAll());            

            List<OrderCart> listOrderCart = cartService.findByAccountId(FlowieeUtil.ACCOUNT_ID);
            modelAndView.addObject("listCart", listOrderCart);

            modelAndView.addObject("donHangRequest", new OrderRequest());
            modelAndView.addObject("donHang", new Order());
            modelAndView.addObject("khachHang", new Customer());
            modelAndView.addObject("cart", new OrderCart());
            modelAndView.addObject("items", new Items());
            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @PostMapping("/ban-hang/cart/{id}/add-items")
    public String addItemsToCart(@PathVariable("id") int idCart, HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
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
    public String updateItemsOfCart(@PathVariable("id") int id, @ModelAttribute("items") Items items) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
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
    public String deleteItemsOfCart(@PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
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
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenThemMoiDonHang()) {
            String thoiGianDatHangString = request.getParameter("thoiGianDatHang");
            if (thoiGianDatHangString != null) {
                orderRequest.setThoiGianDatHang(DateUtil.convertStringToDate(thoiGianDatHangString));
            } else {
                orderRequest.setThoiGianDatHang(new Date());
            }
            List<Integer> listBtspIdInt = new ArrayList<>();
            List<String> listBtspIdString = Arrays.stream(request.getParameter("listBienTheSanPhamId").split(",")).toList();
            for (String idString : listBtspIdString) {
                listBtspIdInt.add(Integer.parseInt(idString));
            }
            orderRequest.setListBienTheSanPham(listBtspIdInt);
            orderRequest.setTrangThaiThanhToan(false);
            orderService.save(orderRequest);
            //Sau khi đã lưu đơn hàng thì xóa all items
            itemsService.findByCartId(orderRequest.getCartId()).forEach(items -> {
                itemsService.delete(items.getId());
            });
            return "redirect:/don-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("donHang") Order order, @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenCapNhatDonHang()) {
            orderService.update(order, id);
            return "redirect:/don-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenCapNhatDonHang()) {
            orderService.delete(id);
            return "redirect:/don-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/thanh-toan/{id}")
    public String thanhToan(@PathVariable("id") int donHangId,
                            @ModelAttribute("donHangThanhToan") OrderPay orderPay) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenCapNhatDonHang()) {
            orderPay.setMaPhieu("PTT" + donHangId + DateUtil.now("yyMMddHHmmss"));
            orderPay.setOrder(orderService.findById(donHangId));
            orderPay.setTrangThaiThanhToan(true);
            donHangThanhToanService.save(orderPay);
            return "redirect:/don-hang/" + donHangId;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @GetMapping("/export")
    public ResponseEntity<?> exportDanhSachDonHang() {
        if (!accountService.isLogin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenExportDonHang()) {
            return orderService.exportDanhSachDonHang();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }
}