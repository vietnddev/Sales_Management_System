package com.flowiee.app.sanpham.controller;

import com.flowiee.app.common.authorization.KiemTraQuyenModuleSanPham;
import com.flowiee.app.common.utils.DateUtil;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.danhmuc.service.HinhThucThanhToanService;
import com.flowiee.app.danhmuc.service.KenhBanHangService;
import com.flowiee.app.danhmuc.service.TrangThaiDonHangService;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.sanpham.entity.*;
import com.flowiee.app.sanpham.model.DonHangRequest;
import com.flowiee.app.sanpham.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/don-hang")
public class DonHangController {
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
    public String findAllDonHang(ModelMap modelMap) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXem()) {
            modelMap.addAttribute("listDonHang", donHangService.findAll());
            modelMap.addAttribute("listBienTheSanPham", bienTheSanPhamService.convertToBienTheSanPhamResponse(bienTheSanPhamService.findAll()));
            modelMap.addAttribute("listKenhBanHang", kenhBanHangService.findAll());
            modelMap.addAttribute("listHinhThucThanhToan", hinhThucThanhToanService.findAll());
            modelMap.addAttribute("listKhachHang", khachHangService.findAll());
            modelMap.addAttribute("listNhanVienBanHang", accountService.findAll());
            modelMap.addAttribute("listTrangThaiDonHang", trangThaiDonHangService.findAll());
            modelMap.addAttribute("donHangRequest", new DonHangRequest());
            modelMap.addAttribute("donHang", new DonHang());
            return PagesUtil.PAGE_DONHANG;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping
    public String FilterListDonHang(ModelMap modelMap, @ModelAttribute("donHangRequest") DonHangRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXem()) {
            modelMap.addAttribute("listDonHang", donHangService.findAll(request.getSearchTxt(),
                                                                                   request.getThoiGianDatHangSearch(),
                                                                                   request.getKenhBanHang(),
                                                                                   request.getTrangThaiDonHang()));
            modelMap.addAttribute("listBienTheSanPham", bienTheSanPhamService.convertToBienTheSanPhamResponse(bienTheSanPhamService.findAll()));
            modelMap.addAttribute("listKenhBanHang", kenhBanHangService.findAll());
            modelMap.addAttribute("listHinhThucThanhToan", hinhThucThanhToanService.findAll());
            modelMap.addAttribute("listKhachHang", khachHangService.findAll());
            modelMap.addAttribute("listNhanVienBanHang", accountService.findAll());
            modelMap.addAttribute("listTrangThaiDonHang", trangThaiDonHangService.findAll());
            modelMap.addAttribute("donHangRequest", new DonHangRequest());
            modelMap.addAttribute("donHang", new DonHang());
            return PagesUtil.PAGE_DONHANG;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @GetMapping("/{id}")
    public String findDonHangDetail(@PathVariable("id") int id, ModelMap modelMap) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXem()) {
            List<DonHangChiTiet> listDonHangDetail = donHangChiTietService.findByDonHangId(id);
            modelMap.addAttribute("donHangDetail", donHangService.findById(id));
            modelMap.addAttribute("listDonHangDetail", donHangChiTietService.convertToDonHangChiTietResponse(listDonHangDetail));
            modelMap.addAttribute("listThanhToan", donHangThanhToanService.findByDonHangId(id));
            modelMap.addAttribute("listHinhThucThanhToan", hinhThucThanhToanService.findAll());
            modelMap.addAttribute("listNhanVienBanHang", accountService.findAll());
            modelMap.addAttribute("donHang", new DonHang());
            modelMap.addAttribute("donHangThanhToan", new DonHangThanhToan());
            return PagesUtil.PAGE_DONHANG_CHITIET;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @GetMapping("/ban-hang")
    public String showPageBanHang(ModelMap modelMap) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenThemMoiDonHang()) {
            List<Cart> cartCurrent = cartService.findByAccountId(accountService.getCurrentAccount().getId());
            if (cartCurrent.isEmpty()) {
                Cart cart = new Cart();
                cart.setCreatedBy(accountService.getCurrentAccount().getId());
                cart.setCreateAt(new Date());
                cartService.save(cart);
            }
            modelMap.addAttribute("listDonHang", donHangService.findAll());
            modelMap.addAttribute("listBienTheSanPham", bienTheSanPhamService.convertToBienTheSanPhamResponse(bienTheSanPhamService.findAll()));
            modelMap.addAttribute("listKenhBanHang", kenhBanHangService.findAll());
            modelMap.addAttribute("listHinhThucThanhToan", hinhThucThanhToanService.findAll());
            modelMap.addAttribute("listKhachHang", khachHangService.findAll());
            modelMap.addAttribute("listNhanVienBanHang", accountService.findAll());
            modelMap.addAttribute("listTrangThaiDonHang", trangThaiDonHangService.findAll());

            List<Cart> listCart = cartService.findByAccountId(accountService.getCurrentAccount().getId());
            modelMap.addAttribute("listCart", listCart);

            modelMap.addAttribute("donHangRequest", new DonHangRequest());
            modelMap.addAttribute("donHang", new DonHang());
            modelMap.addAttribute("khachHang", new KhachHang());
            modelMap.addAttribute("cart", new Cart());
            modelMap.addAttribute("items", new Items());
            return PagesUtil.PAGE_DONHANG_BANHANG;
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
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
}