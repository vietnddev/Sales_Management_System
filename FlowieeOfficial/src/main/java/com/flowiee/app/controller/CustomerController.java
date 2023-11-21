package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.common.utils.PagesUtil;
import com.flowiee.app.config.author.ValidateModuleProduct;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.service.CustomerContactService;
import com.flowiee.app.service.CustomerService;
import com.flowiee.app.service.OrderService;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.entity.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerContactService customerContactService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ValidateModuleProduct validateModuleSanPham;

    @GetMapping
    public ModelAndView findAllCustomer() {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (validateModuleSanPham.readCustomer()) {
            ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_CUSTOMER);
            modelAndView.addObject("listCustomer", customerService.findAll());
            modelAndView.addObject("customer", new Customer());
            return baseView(modelAndView);
        } else {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ModelAndView findCustomerDetail(@PathVariable("id") int id) {
        if (!accountService.isLogin()) {
            return new ModelAndView(PagesUtil.SYS_LOGIN);
        }
        if (!validateModuleSanPham.readCustomer()) {
            return new ModelAndView(PagesUtil.SYS_UNAUTHORIZED);
        }
        if (customerService.findById(id) == null) {
            throw new NotFoundException("Customer not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_CUSTOMER_DETAIL);
        modelAndView.addObject("khachHangDetail", customerService.findById(id));
        modelAndView.addObject("listDonHang", orderService.findByKhachHangId(id));
        return baseView(modelAndView);
    }

    @PostMapping("/insert")
    public String insertCustomer(@ModelAttribute("customer") Customer customer) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleSanPham.insertCustomer()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (customer == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.save(customer);
        return "redirect:/customer";
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@ModelAttribute("khachHang") Customer customer,
                                  @PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleSanPham.updateCustomer()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (customer == null || id <= 0 || customerService.findById(id) == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.update(customer, id);
        return "redirect:/customer";
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleSanPham.deleteCustomer()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (id <= 0 || customerService.findById(id) == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.delete(id);
        return "redirect:/customer";
    }

    @PostMapping("/contact/use-default/{contactId}")
    public String setCustomerContactUseDefault(@RequestParam("customerId") Integer customerId,
                                       @RequestParam("contactCode") String contactCode,
                                       @PathVariable("contactId") Integer contactId) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleSanPham.updateCustomer()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (customerId <= 0 || customerService.findById(customerId) == null) {
            throw new NotFoundException("Customer not found!");
        }
        if (contactId <= 0 || customerContactService.findById(contactId) == null) {
            throw new NotFoundException("Customer contact not found!");
        }
        customerContactService.setContactUseDefault(customerId, contactCode, contactId);
        return "redirect:/customer";
    }

    @PostMapping("/contact/undefault/{contactId}")
    public String setCustomerContactUnUseDefault(@PathVariable("contactId") Integer contactId) {
        if (!accountService.isLogin()) {
            return PagesUtil.SYS_LOGIN;
        }
        if (!validateModuleSanPham.updateCustomer()) {
            return PagesUtil.SYS_UNAUTHORIZED;
        }
        if (contactId <= 0 || customerContactService.findById(contactId) == null) {
            throw new NotFoundException("Customer contact not found!");
        }
        customerContactService.setContactUnUseDefault(contactId);
        return "redirect:/customer";
    }
}