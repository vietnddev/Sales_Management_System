package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.utils.DateUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.danhmuc.service.HinhThucThanhToanService;
import com.flowiee.app.danhmuc.service.KenhBanHangService;
import com.flowiee.app.danhmuc.service.TrangThaiDonHangService;
import com.flowiee.app.hethong.model.module.SystemModule;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.sanpham.entity.*;
import com.flowiee.app.sanpham.model.DonHangRequest;
import com.flowiee.app.sanpham.services.*;
import com.flowiee.app.common.authorization.KiemTraQuyenModuleSanPham;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class DonHangController {
    private static final Logger logger = LoggerFactory.getLogger(DonHangController.class);
    private static final String module = SystemModule.SAN_PHAM.name();

    @Autowired
    private DonHangService donHangService;
    @Autowired
    private ChiTietDonHangService donHangChiTietService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;
    @Autowired
    private KenhBanHangService kenhBanHangService;
    @Autowired
    private HinhThucThanhToanService hinhThucThanhToanService;
    @Autowired
    private KhachHangService khachHangService;
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
            modelAndView.addObject("listDonHang", donHangService.findAll());
            modelAndView.addObject("listBienTheSanPham", bienTheSanPhamService.findAll());
            modelAndView.addObject("listKenhBanHang", kenhBanHangService.findAll());
            modelAndView.addObject("listHinhThucThanhToan", hinhThucThanhToanService.findAll());
            modelAndView.addObject("listKhachHang", khachHangService.findAll());
            modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
            modelAndView.addObject("listTrangThaiDonHang", trangThaiDonHangService.findAll());
            modelAndView.addObject("donHangRequest", new DonHangRequest());
            modelAndView.addObject("donHang", new DonHang());
            return modelAndView;
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @PostMapping
    public ModelAndView FilterListDonHang(@ModelAttribute("donHangRequest") DonHangRequest request) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXem()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_DONHANG);
            modelAndView.addObject("listDonHang", donHangService.findAll(request.getSearchTxt(),
                    request.getThoiGianDatHangSearch(),
                    request.getKenhBanHang(),
                    request.getTrangThaiDonHang()));
            modelAndView.addObject("listBienTheSanPham", bienTheSanPhamService.findAll());
            modelAndView.addObject("listKhachHang", khachHangService.findAll());
            modelAndView.addObject("listNhanVienBanHang", accountService.findAll());

            modelAndView.addObject("selected_kenhBanHang", request.getKenhBanHang() == 0 ? null : kenhBanHangService.findById(request.getKenhBanHang()));
            modelAndView.addObject("listKenhBanHang", kenhBanHangService.findAll());

            modelAndView.addObject("selected_hinhThucThanhToan", request.getHinhThucThanhToan() == 0 ? null : hinhThucThanhToanService.findById(request.getHinhThucThanhToan()));
            modelAndView.addObject("listHinhThucThanhToan", hinhThucThanhToanService.findAll());

            modelAndView.addObject("selected_trangThaiDonHang", request.getTrangThaiDonHang() == 0 ? null : trangThaiDonHangService.findById(request.getTrangThaiDonHang()));
            modelAndView.addObject("listTrangThaiDonHang", trangThaiDonHangService.findAll());

            modelAndView.addObject("donHangRequest", new DonHangRequest());
            modelAndView.addObject("donHang", new DonHang());
            return modelAndView;
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
            modelAndView.addObject("donHangDetail", donHangService.findById(id));
            modelAndView.addObject("listDonHangDetail", donHangChiTietService.findByDonHangId(id));
            modelAndView.addObject("listThanhToan", donHangThanhToanService.findByDonHangId(id));
            modelAndView.addObject("listHinhThucThanhToan", hinhThucThanhToanService.findAll());
            modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
            modelAndView.addObject("donHang", new DonHang());
            modelAndView.addObject("donHangThanhToan", new DonHangThanhToan());
            return modelAndView;
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
            List<Cart> cartCurrent = cartService.findByAccountId(accountService.getCurrentAccount().getId());
            if (cartCurrent.isEmpty()) {
                Cart cart = new Cart();
                cart.setCreatedBy(accountService.getCurrentAccount().getId() + "");
                cartService.save(cart);
            }
            modelAndView.addObject("listDonHang", donHangService.findAll());
            modelAndView.addObject("listBienTheSanPham", bienTheSanPhamService.findAll());
            modelAndView.addObject("listKenhBanHang", kenhBanHangService.findAll());
            modelAndView.addObject("listHinhThucThanhToan", hinhThucThanhToanService.findAll());
            modelAndView.addObject("listKhachHang", khachHangService.findAll());
            modelAndView.addObject("listNhanVienBanHang", accountService.findAll());
            modelAndView.addObject("listTrangThaiDonHang", trangThaiDonHangService.findAll());

            List<Cart> listCart = cartService.findByAccountId(accountService.getCurrentAccount().getId());
            modelAndView.addObject("listCart", listCart);

            modelAndView.addObject("donHangRequest", new DonHangRequest());
            modelAndView.addObject("donHang", new DonHang());
            modelAndView.addObject("khachHang", new KhachHang());
            modelAndView.addObject("cart", new Cart());
            modelAndView.addObject("items", new Items());
            return modelAndView;
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
            items.setCart(cartService.findById(idCart));
            items.setBienTheSanPham(bienTheSanPhamService.findById(Integer.parseInt(bTSanPhamId)));
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
        items.setCart(cartService.findById(id));
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
    public String insert(@ModelAttribute("donHangRequest") DonHangRequest donHangRequest,
                         HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenThemMoiDonHang()) {
            String thoiGianDatHangString = request.getParameter("thoiGianDatHang");
            if (thoiGianDatHangString != null) {
                donHangRequest.setThoiGianDatHang(DateUtil.convertStringToDate(thoiGianDatHangString));
            } else {
                donHangRequest.setThoiGianDatHang(new Date());
            }
            List<Integer> listBtspIdInt = new ArrayList<>();
            List<String> listBtspIdString = Arrays.stream(request.getParameter("listBienTheSanPhamId").split(",")).toList();
            for (String idString : listBtspIdString) {
                listBtspIdInt.add(Integer.parseInt(idString));
            }
            donHangRequest.setListBienTheSanPham(listBtspIdInt);
            donHangRequest.setTrangThaiThanhToan(false);
            donHangService.save(donHangRequest);
            //Sau khi đã lưu đơn hàng thì xóa all items
            itemsService.findByCartId(donHangRequest.getCartId()).forEach(items -> {
                itemsService.delete(items.getId());
            });
            return "redirect:/don-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("donHang") DonHang donHang, @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenCapNhatDonHang()) {
            donHangService.update(donHang, id);
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
            donHangService.delete(id);
            return "redirect:/don-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/thanh-toan/{id}")
    public String thanhToan(@PathVariable("id") int donHangId,
                            @ModelAttribute("donHangThanhToan") DonHangThanhToan donHangThanhToan) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenCapNhatDonHang()) {
            donHangThanhToan.setMaPhieu("PTT" + donHangId + DateUtil.now("yyMMddHHmmss"));
            donHangThanhToan.setDonHang(donHangService.findById(donHangId));
            donHangThanhToan.setTrangThaiThanhToan(true);
            donHangThanhToanService.save(donHangThanhToan);
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
            return donHangService.exportDanhSachDonHang();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }
}