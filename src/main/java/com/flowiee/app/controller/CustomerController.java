package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.entity.CustomerContact;
import com.flowiee.app.utils.EndPointUtil;
import com.flowiee.app.utils.PagesUtil;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.security.ValidateModuleProduct;
import com.flowiee.app.service.CustomerContactService;
import com.flowiee.app.service.CustomerService;
import com.flowiee.app.service.OrderService;
import com.flowiee.app.entity.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(EndPointUtil.PRO_CUSTOMER)
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
        modelAndView.addObject("listCustomer", customerService.findAll(null, null, null, null, null, null));
        modelAndView.addObject("customer", new Customer());
        return baseView(modelAndView);
    }

    @GetMapping("/{id}")
    public ModelAndView findCustomerDetail(@PathVariable("id") Integer customerId) {
        validateModuleSanPham.readCustomer(true);
        if (customerId <= 0 || customerService.findById(customerId) == null) {
            throw new NotFoundException("Customer not found!");
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_CUSTOMER_DETAIL);
        modelAndView.addObject("khachHangDetail", customerService.findById(customerId));
        modelAndView.addObject("listCustomerContact", customerContactService.findByCustomerId(customerId));
        modelAndView.addObject("listDonHang", orderService.findByKhachHangId(customerId));
        return baseView(modelAndView);
    }

    @PostMapping("/insert")
    public ModelAndView insertCustomer(@ModelAttribute("customer") Customer customer) {
        validateModuleSanPham.insertCustomer(true);
        if (customer == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.save(customer);
        return new ModelAndView("redirect:/customer");
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateCustomer(@ModelAttribute("khachHang") Customer customer, @PathVariable("id") Integer customerId) {
        validateModuleSanPham.updateCustomer(true);
        if (customer == null || customerId <= 0 || customerService.findById(customerId) == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.update(customer, customerId);
        return new ModelAndView("redirect:/customer");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteCustomer(@PathVariable("id") Integer id) {
        validateModuleSanPham.deleteCustomer(true);
        if (id <= 0 || customerService.findById(id) == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.delete(id);
        return new ModelAndView("redirect:/customer");
    }

    @PostMapping("/contact/insert")
    public ModelAndView insertCustomerContact(@ModelAttribute("customerContact") CustomerContact customerContact, HttpServletRequest request) {
        validateModuleSanPham.updateCustomer(true);
        if (customerContact == null || customerContact.getCustomer() == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerContactService.save(customerContact);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/contact/update/{id}")
    public ModelAndView updateCustomerContact(@ModelAttribute("customerContact") CustomerContact customerContact,
                                              @PathVariable("id") Integer customerContactId,
                                              HttpServletRequest request) {
        validateModuleSanPham.updateCustomer(true);
        if (customerContact == null || customerContact.getCustomer() == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerContactService.update(customerContact, customerContactId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/contact/delete/{id}")
    public ModelAndView updateCustomerContact(@PathVariable("id") Integer customerContactId,
                                              HttpServletRequest request) {
        validateModuleSanPham.updateCustomer(true);
        if (customerContactId == null || customerContactService.findById(customerContactId) == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerContactService.delete(customerContactId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/contact/use-default/{contactId}")
    public ModelAndView setCustomerContactUseDefault(@RequestParam("customerId") Integer customerId,
                                                     @RequestParam("contactCode") String contactCode,
                                                     @PathVariable("contactId") Integer contactId,
                                                     HttpServletRequest request) {
        validateModuleSanPham.updateCustomer(true);
        if (customerId <= 0 || customerService.findById(customerId) == null) {
            throw new NotFoundException("Customer not found!");
        }
        if (contactId <= 0 || customerContactService.findById(contactId) == null) {
            throw new NotFoundException("Customer contact not found!");
        }
        customerContactService.setContactUseDefault(customerId, contactCode, contactId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/contact/undefault/{contactId}")
    public ModelAndView setCustomerContactUnUseDefault(@PathVariable("contactId") Integer contactId,
                                                       HttpServletRequest request) {
        validateModuleSanPham.updateCustomer(true);
        if (contactId <= 0 || customerContactService.findById(contactId) == null) {
            throw new NotFoundException("Customer contact not found!");
        }
        customerContactService.setContactUnUseDefault(contactId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }
}