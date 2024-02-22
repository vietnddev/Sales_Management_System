package com.flowiee.app.controller;

import com.flowiee.app.base.BaseController;
import com.flowiee.app.dto.CustomerDTO;
import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.CustomerContact;
import com.flowiee.app.exception.AppException;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.model.ApiResponse;
import com.flowiee.app.service.CustomerService;
import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/customer")
@Tag(name = "Customer API", description = "Quản lý khách hàng")
public class CustomerController extends BaseController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Find all customers")
    @GetMapping("/all")
    public ApiResponse<List<CustomerDTO>> findCustomers(@RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                        @RequestParam(value = "pageNum", required = false) Integer pageNum) {
        try {
            if (!super.validateModuleProduct.readCustomer(true)) {
                return null;
            }
            if (pageSize != null && pageNum != null) {
                Page<Customer> customers = customerService.findAllCustomer(pageSize, pageNum - 1);
                return ApiResponse.ok(customerService.convertToDTOAndSetContactDefault(customers), pageNum, pageSize, customers.getTotalPages(), customers.getTotalElements());
            } else {
                Page<Customer> customers = customerService.findAllCustomer(null, null);
                return ApiResponse.ok(customerService.convertToDTOAndSetContactDefault(customers), 1, 0, customers.getTotalPages(), customers.getTotalElements());
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "customer"));
        }
    }

    @Operation(summary = "Find detail customer")
    @GetMapping("/{customerId}")
    public ApiResponse<Customer> findDetailCustomer(@PathVariable("customerId") Integer customerId) {
        try {
            return ApiResponse.ok(customerService.findCustomerById(customerId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "customer"));
        }
    }

    @Operation(summary = "Create customer")
    @PostMapping("/insert")
    public ApiResponse<String> createCustomer(@RequestBody CustomerDTO customer) {
        if (!super.validateModuleProduct.insertCustomer(true)) {
            return null;
        }
        try {
            if (customer == null) {
                throw new BadRequestException();
            }
            customerService.saveCustomer(Customer.fromCustomerDTO(customer));
            return ApiResponse.ok(MessageUtils.CREATE_SUCCESS);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "customer"));
        }
    }

    @Operation(summary = "Update customer")
    @PutMapping("/update/{customerId}")
    public ApiResponse<String> updateCustomer(@RequestBody CustomerDTO customer, @PathVariable("customerId") Integer customerId) {
        if (!super.validateModuleProduct.updateCustomer(true)) {
            return null;
        }
        try {
            if (customer == null || customerId <= 0 || customerService.findCustomerById(customerId) == null) {
                throw new BadRequestException();
            }
            customerService.updateCustomer(Customer.fromCustomerDTO(customer), customerId);
            return ApiResponse.ok(MessageUtils.UPDATE_SUCCESS);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "customer"));
        }
    }

    @Operation(summary = "Delete customer")
    @DeleteMapping("/delete/{customerId}")
    public ApiResponse<String> deleteCustomer(@PathVariable("customerId") Integer customerId) {
        if (!super.validateModuleProduct.deleteCustomer(true)) {
            return null;
        }
        try {
            if (customerId <= 0 || customerService.findCustomerById(customerId) == null) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(customerService.deleteCustomer(customerId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "customer"));
        }
    }

    @Operation(summary = "Find contacts of customer")
    @GetMapping("/{customerId}/contact")
    public ApiResponse<List<CustomerContact>> findContactsOfCustomer(@PathVariable("customerId") Integer customerId) {
        if (!super.validateModuleProduct.readCustomer(true)) {
            return null;
        }
        try {
            if (customerId <= 0 || customerService.findCustomerById(customerId) == null) {
                throw new BadRequestException();
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
            return ApiResponse.ok(listContacts);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "contact"));
        }
    }

    @Operation(summary = "Create contact")
    @PostMapping("/contact/insert")
    public ApiResponse<CustomerContact> insertContact(@RequestBody CustomerContact customerContact) {
        if (!super.validateModuleProduct.updateCustomer(true)) {
            return null;
        }
        try {
            if (customerContact == null || customerContact.getCustomer() == null) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(customerService.saveContact(customerContact, customerContact.getCustomer().getId()));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "contact"));
        }
    }

    @Operation(summary = "Update contact")
    @PutMapping("/contact/update/{contactId}")
    public ApiResponse<CustomerContact> updateContact(@RequestBody CustomerContact customerContact, @PathVariable("contactId") Integer contactId) {
        if (!super.validateModuleProduct.updateCustomer(true)) {
            return null;
        }
        try {
            if (customerContact == null || customerContact.getCustomer() == null || customerService.findContactById(contactId) == null) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(customerService.updateContact(customerContact, contactId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "contact"));
        }
    }

    @Operation(summary = "Delete contact")
    @DeleteMapping("/contact/delete/{contactId}")
    public ApiResponse<String> deleteContact(@PathVariable("contactId") Integer contactId) {
        if (!super.validateModuleProduct.updateCustomer(true)) {
            return null;
        }
        try {
            if (contactId <= 0 || customerService.findContactById(contactId) == null) {
                throw new BadRequestException();
            }
            //Check in use
            return ApiResponse.ok(customerService.deleteContact(contactId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.DELETE_ERROR_OCCURRED, "contact"));
        }
    }

    @Operation(summary = "Update contact use default")
    @PatchMapping("/contact/use-default/{contactId}")
    public ApiResponse<CustomerContact> setContactUseDefault(@RequestParam("customerId") Integer customerId,
                                                             @RequestParam("contactCode") String contactCode,
                                                             @PathVariable("contactId") Integer contactId) {
        if (!super.validateModuleProduct.updateCustomer(true)) {
            return null;
        }
        try {
            if (customerId <= 0 || contactId <= 0 || customerService.findCustomerById(customerId) == null || customerService.findContactById(contactId) == null) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(customerService.setContactUseDefault(customerId, contactCode, contactId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "contact"));
        }
    }

    @Operation(summary = "Update contact un-use default")
    @PatchMapping("/contact/undefault/{contactId}")
    public ApiResponse<CustomerContact> setContactUnUseDefault(@PathVariable("contactId") Integer contactId) {
        if (!super.validateModuleProduct.updateCustomer(true)) {
            return null;
        }
        try {
            if (contactId <= 0 || customerService.findContactById(contactId) == null) {
                throw new BadRequestException();
            }
            return ApiResponse.ok(customerService.setContactUnUseDefault(contactId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "contact"));
        }
    }
}