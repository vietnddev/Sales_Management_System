package com.flowiee.app.product.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.product.entity.Customer;
import com.flowiee.app.system.service.AccountService;
import com.flowiee.app.product.services.OrderService;
import com.flowiee.app.product.services.CustomerService;
import com.flowiee.app.config.KiemTraQuyenModuleSanPham;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/khach-hang")
public class CustomerController extends BaseController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private KiemTraQuyenModuleSanPham kiemTraQuyenModuleSanPham;

    @GetMapping
    public ModelAndView findAllKhachHang() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXemKhachHang()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_KHACHHANG);
            modelAndView.addObject("listKhachHang", customerService.findAll());
            modelAndView.addObject("khachHang", new Customer());            
            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ModelAndView findChiTietKhachHang(@PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.PAGE_LOGIN);
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXemKhachHang()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_KHACHHANG_CHITIET);
            modelAndView.addObject("khachHangDetail", customerService.findById(id));
            modelAndView.addObject("listDonHang", orderService.findByKhachHangId(id));
            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
    }

    @PostMapping("/create")
    public String createKhachHang(@ModelAttribute("khachHang") Customer customer,
                                  HttpServletRequest request) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenThemMoiKhachHang()) {
            customerService.save(customer);
            return "redirect:/khach-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/update/{id}")
    public String createKhachHang(@ModelAttribute("khachHang") Customer customer,
                                  @PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenCapNhatKhachHang()) {
            customerService.update(customer, id);
            return "redirect:/khach-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteKhachHang(@PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (kiemTraQuyenModuleSanPham.kiemTraQuyenXoaKhachHang()) {
            customerService.delete(id);
            return "redirect:/khach-hang";
        } else {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
    }
}