package com.flowiee.app.controller.product;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.service.product.CustomerService;
import com.flowiee.app.service.product.OrderService;
import com.flowiee.app.service.system.AccountService;
import com.flowiee.app.config.KiemTraQuyenModuleSanPham;
import com.flowiee.app.entity.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        if (!kiemTraQuyenModuleSanPham.kiemTraQuyenXemKhachHang()) {
            return new ModelAndView(PagesUtil.PAGE_UNAUTHORIZED);
        }
        if (customerService.findById(id) == null) {
            throw new NotFoundException("Customer not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PAGE_KHACHHANG_CHITIET);
        modelAndView.addObject("khachHangDetail", customerService.findById(id));
        modelAndView.addObject("listDonHang", orderService.findByKhachHangId(id));
        return baseView(modelAndView);
    }

    @PostMapping("/create")
    public String createKhachHang(@ModelAttribute("khachHang") Customer customer) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (!kiemTraQuyenModuleSanPham.kiemTraQuyenThemMoiKhachHang()) {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
        if (customer == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.save(customer);
        return "redirect:/khach-hang";
    }

    @PostMapping("/update/{id}")
    public String createKhachHang(@ModelAttribute("khachHang") Customer customer,
                                  @PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (!kiemTraQuyenModuleSanPham.kiemTraQuyenCapNhatKhachHang()) {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
        if (customer == null || id <= 0 || customerService.findById(id) == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.update(customer, id);
        return "redirect:/khach-hang";
    }

    @PostMapping("/delete/{id}")
    public String deleteKhachHang(@PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return PagesUtil.PAGE_LOGIN;
        }
        if (!kiemTraQuyenModuleSanPham.kiemTraQuyenXoaKhachHang()) {
            return PagesUtil.PAGE_UNAUTHORIZED;
        }
        if (id <= 0 || customerService.findById(id) == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.delete(id);
        return "redirect:/khach-hang";
    }
}