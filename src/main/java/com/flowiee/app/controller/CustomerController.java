package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.utils.PagesUtil;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.security.ValidateModuleProduct;
import com.flowiee.app.service.CustomerContactService;
import com.flowiee.app.service.CustomerService;
import com.flowiee.app.service.OrderService;
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
    private OrderService orderService;
    @Autowired
    private ValidateModuleProduct validateModuleSanPham;

    @GetMapping
    public ModelAndView findAllCustomer() {
        validateModuleSanPham.readCustomer(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_CUSTOMER);
        modelAndView.addObject("listCustomer", customerService.findAll());
        modelAndView.addObject("customer", new Customer());
        return baseView(modelAndView);
    }

    @GetMapping("/{id}")
    public ModelAndView findCustomerDetail(@PathVariable("id") int id) {
        validateModuleSanPham.readCustomer(true);
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
        validateModuleSanPham.insertCustomer(true);
        if (customer == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.save(customer);
        return "redirect:/customer";
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@ModelAttribute("khachHang") Customer customer,
                                 @PathVariable("id") Integer id) {
        validateModuleSanPham.updateCustomer(true);
        if (customer == null || id <= 0 || customerService.findById(id) == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.update(customer, id);
        return "redirect:/customer";
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id) {
        validateModuleSanPham.deleteCustomer(true);
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
        validateModuleSanPham.updateCustomer(true);
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
        validateModuleSanPham.updateCustomer(true);
        if (contactId <= 0 || customerContactService.findById(contactId) == null) {
            throw new NotFoundException("Customer contact not found!");
        }
        customerContactService.setContactUnUseDefault(contactId);
        return "redirect:/customer";
    }
}