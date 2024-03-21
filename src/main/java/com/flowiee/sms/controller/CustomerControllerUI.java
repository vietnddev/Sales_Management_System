package com.flowiee.sms.controller;

import com.flowiee.sms.core.BaseController;
import com.flowiee.sms.model.dto.CustomerDTO;
import com.flowiee.sms.entity.CustomerContact;
import com.flowiee.sms.utils.*;
import com.flowiee.sms.core.exception.NotFoundException;
import com.flowiee.sms.core.vld.ValidateModuleProduct;
import com.flowiee.sms.service.CustomerService;
import com.flowiee.sms.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(EndPointUtil.PRO_CUSTOMER)
public class CustomerControllerUI extends BaseController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ValidateModuleProduct validateProductModule;

    @GetMapping
    public ModelAndView searchCustomer(@Nullable @RequestParam("name") String name,
                                       @Nullable @RequestParam("sex") String sex,
                                       @Nullable @RequestParam("birthday") String birthday,
                                       @Nullable @RequestParam("phone") String phone,
                                       @Nullable @RequestParam("email") String email,
                                       @Nullable @RequestParam("address") String address) {
        validateProductModule.readCustomer(true);
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_CUSTOMER);
        modelAndView.addObject("listCustomer", customerService.findAllCustomer(name, sex, birthday != null ? DateUtils.convertStringToDate(birthday, "YYYY/MM/dd") : null, phone, email, address));
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
        validateProductModule.readCustomer(true);
        if (customerId <= 0 || customerService.findCustomerById(customerId) == null) {
            throw new NotFoundException("Customer not found!");
        }
        List<CustomerContact> listContacts = customerService.findContactsByCustomerId(customerId);
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
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_CUSTOMER_DETAIL);
        modelAndView.addObject("customerDetail", customerService.findCustomerById(customerId));
        modelAndView.addObject("listCustomerContact", listContacts);
        modelAndView.addObject("listDonHang", orderService.findOrdersByCustomerId(customerId));
        return baseView(modelAndView);
    }

    @PostMapping("/insert")
    public ModelAndView insertCustomer(CustomerDTO customer) {
        validateProductModule.insertCustomer(true);
        if (customer == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.saveCustomer(customer);
        return new ModelAndView("redirect:/customer");
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateCustomer(@ModelAttribute("customer") CustomerDTO customer, @PathVariable("id") Integer customerId) {
        validateProductModule.updateCustomer(true);
        if (customer == null || customerId <= 0 || customerService.findCustomerById(customerId) == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.updateCustomer(customer, customerId);
        return new ModelAndView("redirect:/customer");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteCustomer(@PathVariable("id") Integer id) {
        validateProductModule.deleteCustomer(true);
        if (id <= 0 || customerService.findCustomerById(id) == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.deleteCustomer(id);
        return new ModelAndView("redirect:/customer");
    }

    @PostMapping("/contact/insert")
    public ModelAndView insertCustomerContact(@ModelAttribute("customerContact") CustomerContact customerContact, HttpServletRequest request) {
        validateProductModule.updateCustomer(true);
        if (customerContact == null || customerContact.getCustomer() == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.saveContact(customerContact, customerContact.getCustomer().getId());
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/contact/update/{id}")
    public ModelAndView updateCustomerContact(@ModelAttribute("customerContact") CustomerContact customerContact,
                                              @PathVariable("id") Integer customerContactId,
                                              HttpServletRequest request) {
        validateProductModule.updateCustomer(true);
        if (customerContact == null || customerContact.getCustomer() == null) {
            throw new NotFoundException("Customer not found!");
        }
        customerService.updateContact(customerContact, customerContactId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/contact/delete/{id}")
    public ModelAndView updateCustomerContact(@PathVariable("id") Integer customerContactId,
                                              HttpServletRequest request) {
        validateProductModule.updateCustomer(true);
        if (customerContactId == null || customerService.findContactById(customerContactId) == null) {
            throw new NotFoundException("Customer not found!");
        }
        //Kiểm tra nếu đã được sử dụng thì không cho xóa
        customerService.deleteContact(customerContactId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/contact/use-default/{contactId}")
    public ModelAndView setCustomerContactUseDefault(@RequestParam("customerId") Integer customerId,
                                                     @RequestParam("contactCode") String contactCode,
                                                     @PathVariable("contactId") Integer contactId,
                                                     HttpServletRequest request) {
        validateProductModule.updateCustomer(true);
        if (customerId <= 0 || customerService.findCustomerById(customerId) == null) {
            throw new NotFoundException("Customer not found!");
        }
        if (contactId <= 0 || customerService.findContactById(contactId) == null) {
            throw new NotFoundException("Customer contact not found!");
        }
        customerService.setContactUseDefault(customerId, contactCode, contactId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/contact/undefault/{contactId}")
    public ModelAndView setCustomerContactUnUseDefault(@PathVariable("contactId") Integer contactId,
                                                       HttpServletRequest request) {
        validateProductModule.updateCustomer(true);
        if (contactId <= 0 || customerService.findContactById(contactId) == null) {
            throw new NotFoundException("Customer contact not found!");
        }
        customerService.setContactUnUseDefault(contactId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }
}