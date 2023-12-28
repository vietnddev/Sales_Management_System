package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.CustomerDTO;
import com.flowiee.app.entity.CustomerContact;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.EndPointUtil;
import com.flowiee.app.utils.PagesUtil;
import com.flowiee.app.exception.NotFoundException;
import com.flowiee.app.security.ValidateModuleProduct;
import com.flowiee.app.service.CustomerContactService;
import com.flowiee.app.service.CustomerService;
import com.flowiee.app.service.OrderService;
import com.flowiee.app.entity.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
    public ModelAndView searchCustomer(@Nullable @RequestParam("name") String name,
                                       @Nullable @RequestParam("sex") String sex,
                                       @Nullable @RequestParam("birthday") String birthday,
                                       @Nullable @RequestParam("phone") String phone,
                                       @Nullable @RequestParam("email") String email,
                                       @Nullable @RequestParam("address") String address) {
        validateModuleSanPham.readCustomer(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_CUSTOMER);
        modelAndView.addObject("listCustomer", customerService.findAll(name, sex, birthday != null ? CommonUtil.convertStringToDate(birthday, "YYYY/MM/dd") : null, phone, email, address));
        modelAndView.addObject("customer", new CustomerDTO());
        modelAndView.addObject("filter_name", name);
        modelAndView.addObject("filter_sex", sex);
        modelAndView.addObject("filter_birthday", birthday);
        modelAndView.addObject("filter_phone", phone);
        modelAndView.addObject("filter_email", email);
        modelAndView.addObject("filter_address", address);
        return baseView(modelAndView);
    }

    @GetMapping("/{id}")
    public ModelAndView findCustomerDetail(@PathVariable("id") Integer customerId) {
        validateModuleSanPham.readCustomer(true);
        if (customerId <= 0 || customerService.findById(customerId) == null) {
            throw new NotFoundException("Customer not found!");
        }
        List<CustomerContact> listContacts = customerContactService.findByCustomerId(customerId);
        for (CustomerContact c : listContacts) {
            if (AppConstants.CONTACT_TYPE.P.name().equals(c.getCode())) {
                c.setCode(AppConstants.CONTACT_TYPE.P.getLabel());
            }
            if (AppConstants.CONTACT_TYPE.E.name().equals(c.getCode())) {
                c.setCode(AppConstants.CONTACT_TYPE.E.getLabel());
            }
            if (AppConstants.CONTACT_TYPE.A.name().equals(c.getCode())) {
                c.setCode(AppConstants.CONTACT_TYPE.A.getLabel());
            }
        }
        ModelAndView modelAndView = new ModelAndView(PagesUtil.PRO_CUSTOMER_DETAIL);
        modelAndView.addObject("customerDetail", customerService.findById(customerId));
        modelAndView.addObject("listCustomerContact", listContacts);
        modelAndView.addObject("listDonHang", orderService.findByKhachHangId(customerId));
        return baseView(modelAndView);
    }

    @PostMapping("/insert")
    public ModelAndView insertCustomer(@ModelAttribute("customer") CustomerDTO customer) {
        validateModuleSanPham.insertCustomer(true);
        if (customer == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.save(Customer.fromCustomerDTO(customer));
        return new ModelAndView("redirect:/customer");
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateCustomer(@ModelAttribute("customer") CustomerDTO customer, @PathVariable("id") Integer customerId) {
        validateModuleSanPham.updateCustomer(true);
        if (customer == null || customerId <= 0 || customerService.findById(customerId) == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.update(Customer.fromCustomerDTO(customer), customerId);
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