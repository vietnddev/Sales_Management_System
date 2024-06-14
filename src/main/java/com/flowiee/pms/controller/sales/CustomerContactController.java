package com.flowiee.pms.controller.sales;

import com.flowiee.pms.controller.BaseController;
import com.flowiee.pms.entity.sales.CustomerContact;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.AppResponse;
import com.flowiee.pms.service.sales.CustomerContactService;
import com.flowiee.pms.service.sales.CustomerService;
import com.flowiee.pms.utils.MessageUtils;
import com.flowiee.pms.utils.constants.ContactType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${app.api.prefix}/customer")
@Tag(name = "Customer API", description = "Quản lý thông tin liên hệ khách hàng")
public class CustomerContactController extends BaseController {
    private final CustomerService        customerService;
    private final CustomerContactService customerContactService;

    public CustomerContactController(CustomerService customerService, CustomerContactService customerContactService) {
        this.customerService = customerService;
        this.customerContactService = customerContactService;
    }

    @Operation(summary = "Find contacts of customer")
    @GetMapping("/{customerId}/contact")
    @PreAuthorize("@vldModuleSales.readCustomer(true)")
    public AppResponse<List<CustomerContact>> findContactsOfCustomer(@PathVariable("customerId") Integer customerId) {
        try {
            if (customerId <= 0 || customerService.findById(customerId).isEmpty()) {
                throw new BadRequestException();
            }
            List<CustomerContact> listContacts = customerContactService.findContacts(customerId);
            for (CustomerContact c : listContacts) {
                if (ContactType.P.name().equals(c.getCode())) {
                    c.setCode(ContactType.P.getLabel());
                }
                if (ContactType.E.name().equals(c.getCode())) {
                    c.setCode(ContactType.E.getLabel());
                }
                if (ContactType.A.name().equals(c.getCode())) {
                    c.setCode(ContactType.A.getLabel());
                }
            }
            return success(listContacts);
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.SEARCH_ERROR_OCCURRED, "contact"), ex);
        }
    }

    @Operation(summary = "Create contact")
    @PostMapping("/contact/insert")
    @PreAuthorize("@vldModuleSales.updateCustomer(true)")
    public AppResponse<CustomerContact> insertContact(@RequestBody CustomerContact customerContact) {
        try {
            if (customerContact == null || customerContact.getCustomer() == null) {
                throw new BadRequestException();
            }
            return success(customerContactService.save(customerContact));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.CREATE_ERROR_OCCURRED, "contact"), ex);
        }
    }

    @Operation(summary = "Update contact")
    @PutMapping("/contact/update/{contactId}")
    @PreAuthorize("@vldModuleSales.updateCustomer(true)")
    public AppResponse<CustomerContact> updateContact(@RequestBody CustomerContact customerContact, @PathVariable("contactId") Integer contactId) {
        try {
            if (customerContact == null || customerContact.getCustomer() == null || customerContactService.findById(contactId).isEmpty()) {
                throw new BadRequestException();
            }
            return success(customerContactService.update(customerContact, contactId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "contact"), ex);
        }
    }

    @Operation(summary = "Delete contact")
    @DeleteMapping("/contact/delete/{contactId}")
    @PreAuthorize("@vldModuleSales.updateCustomer(true)")
    public AppResponse<String> deleteContact(@PathVariable("contactId") Integer contactId) {
        return success(customerContactService.delete(contactId));
    }

    @Operation(summary = "Update contact use default")
    @PatchMapping("/contact/use-default/{contactId}")
    @PreAuthorize("@vldModuleSales.updateCustomer(true)")
    public AppResponse<CustomerContact> setContactUseDefault(@RequestParam("customerId") Integer customerId,
                                                             @RequestParam("contactCode") String contactCode,
                                                             @PathVariable("contactId") Integer contactId) {
        try {
            if (customerId <= 0 || contactId <= 0 || customerService.findById(customerId).isEmpty() || customerContactService.findById(contactId).isEmpty()) {
                throw new BadRequestException();
            }
            return success(customerContactService.enableContactUseDefault(customerId, contactCode, contactId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "contact"), ex);
        }
    }

    @Operation(summary = "Update contact un-use default")
    @PatchMapping("/contact/undefault/{contactId}")
    @PreAuthorize("@vldModuleSales.updateCustomer(true)")
    public AppResponse<CustomerContact> setContactUnUseDefault(@PathVariable("contactId") Integer contactId) {
        try {
            if (contactId <= 0 || customerContactService.findById(contactId).isEmpty()) {
                throw new BadRequestException();
            }
            return success(customerContactService.disableContactUnUseDefault(contactId));
        } catch (RuntimeException ex) {
            throw new AppException(String.format(MessageUtils.UPDATE_ERROR_OCCURRED, "contact"), ex);
        }
    }
}