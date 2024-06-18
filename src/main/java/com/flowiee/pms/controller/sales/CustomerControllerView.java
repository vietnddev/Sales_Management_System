package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.model.dto.CustomerDTO;
import com.flowiee.pms.entity.sales.CustomerContact;
import com.flowiee.pms.service.sales.CustomerContactService;
import com.flowiee.pms.utils.*;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.service.sales.CustomerService;
import com.flowiee.pms.service.sales.OrderService;

import com.flowiee.pms.utils.constants.ContactType;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerControllerView extends BaseController {
    private final OrderService           orderService;
    private final CustomerService        customerService;
    private final CustomerContactService customerContactService;

    public CustomerControllerView(OrderService orderService, CustomerService customerService, CustomerContactService customerContactService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.customerContactService = customerContactService;
    }

    @GetMapping
    @PreAuthorize("@vldModuleSales.readCustomer(true)")
    public ModelAndView searchCustomer(@Nullable @RequestParam("name") String name,
                                       @Nullable @RequestParam("sex") String sex,
                                       @Nullable @RequestParam("birthday") String birthday,
                                       @Nullable @RequestParam("phone") String phone,
                                       @Nullable @RequestParam("email") String email,
                                       @Nullable @RequestParam("address") String address) {
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_CUSTOMER);
        modelAndView.addObject("listCustomer", customerService.findAll(-1, -1, name, sex, birthday != null ? DateUtils.convertStringToDate(birthday, "YYYY/MM/dd") : null, phone, email, address));
        modelAndView.addObject("filter_name", name);
        modelAndView.addObject("filter_sex", sex);
        modelAndView.addObject("filter_birthday", birthday);
        modelAndView.addObject("filter_phone", phone);
        modelAndView.addObject("filter_email", email);
        modelAndView.addObject("filter_address", address);
        return baseView(modelAndView);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@vldModuleSales.readCustomer(true)")
    public ModelAndView findCustomerDetail(@PathVariable("id") Integer customerId) {
        Optional<CustomerDTO> customerDTO = customerService.findById(customerId);
        if (customerDTO.isEmpty()) {
            throw new ResourceNotFoundException("Customer not found");
        }
        List<CustomerContact> listContacts = customerContactService.findContacts(customerId);
        listContacts.forEach(c -> {
            if (ContactType.P.name().equals(c.getCode())) c.setCode(ContactType.P.getLabel());
            if (ContactType.E.name().equals(c.getCode())) c.setCode(ContactType.E.getLabel());
            if (ContactType.A.name().equals(c.getCode())) c.setCode(ContactType.A.getLabel());
        });
        ModelAndView modelAndView = new ModelAndView(PagesUtils.PRO_CUSTOMER_DETAIL);
        modelAndView.addObject("customerDetail", customerDTO.get());
        modelAndView.addObject("listCustomerContact", listContacts);
        modelAndView.addObject("listDonHang", orderService.findAll());
        return baseView(modelAndView);
    }

    @PostMapping("/insert")
    @PreAuthorize("@vldModuleSales.insertCustomer(true)")
    public ModelAndView insertCustomer(CustomerDTO customer) {
        customerService.save(customer);
        return new ModelAndView("redirect:/customer");
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("@vldModuleSales.updateCustomer(true)")
    public ModelAndView updateCustomer(@ModelAttribute("customer") CustomerDTO customer, @PathVariable("id") Integer customerId) {
        if (customer == null || customerId <= 0 || customerService.findById(customerId).isEmpty()) {
            throw new ResourceNotFoundException("Customer not found!");
        }
        customerService.update(customer, customerId);
        return new ModelAndView("redirect:/customer");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("@vldModuleSales.deleteCustomer(true)")
    public ModelAndView deleteCustomer(@PathVariable("id") Integer id) {
        customerService.delete(id);
        return new ModelAndView("redirect:/customer");
    }

    @PostMapping("/contact/insert")
    @PreAuthorize("@vldModuleSales.updateCustomer(true)")
    public ModelAndView insertCustomerContact(@ModelAttribute("customerContact") CustomerContact customerContact, HttpServletRequest request) {
        if (customerContact == null || customerContact.getCustomer() == null) {
            throw new ResourceNotFoundException("Customer not found!");
        }
        customerContactService.save(customerContact);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/contact/update/{id}")
    @PreAuthorize("@vldModuleSales.updateCustomer(true)")
    public ModelAndView updateCustomerContact(@ModelAttribute("customerContact") CustomerContact customerContact,
                                              @PathVariable("id") Integer customerContactId,
                                              HttpServletRequest request) {
        if (customerContact == null || customerContact.getCustomer() == null) {
            throw new ResourceNotFoundException("Customer not found!");
        }
        customerContactService.update(customerContact, customerContactId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/contact/delete/{id}")
    @PreAuthorize("@vldModuleSales.updateCustomer(true)")
    public ModelAndView updateCustomerContact(@PathVariable("id") Integer customerContactId, HttpServletRequest request) {
        customerContactService.delete(customerContactId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/contact/use-default/{contactId}")
    @PreAuthorize("@vldModuleSales.updateCustomer(true)")
    public ModelAndView setCustomerContactUseDefault(@RequestParam("customerId") Integer customerId,
                                                     @RequestParam("contactCode") String contactCode,
                                                     @PathVariable("contactId") Integer contactId,
                                                     HttpServletRequest request) {
        customerContactService.enableContactUseDefault(customerId, contactCode, contactId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }

    @PostMapping("/contact/undefault/{contactId}")
    @PreAuthorize("@vldModuleSales.updateCustomer(true)")
    public ModelAndView setCustomerContactUnUseDefault(@PathVariable("contactId") Integer contactId, HttpServletRequest request) {
        if (contactId <= 0 || customerContactService.findById(contactId).isEmpty()) {
            throw new ResourceNotFoundException("Customer contact not found!");
        }
        customerContactService.disableContactUnUseDefault(contactId);
        return new ModelAndView("redirect:" + request.getHeader("referer"));
    }
}