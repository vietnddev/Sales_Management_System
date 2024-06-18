package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.CustomerContact;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.repository.sales.CustomerContactRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.CustomerContactService;
import com.flowiee.pms.service.sales.CustomerService;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.utils.constants.MasterObject;
import com.flowiee.pms.utils.constants.MessageCode;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerContactServiceImpl extends BaseService implements CustomerContactService {
    private final CustomerContactRepository customerContactRepo;
    private final CustomerService           customerService;

    public CustomerContactServiceImpl(CustomerContactRepository customerContactRepo, @Lazy CustomerService customerService) {
        this.customerContactRepo = customerContactRepo;
        this.customerService = customerService;
    }

    @Override
    public List<CustomerContact> findAll() {
        return customerContactRepo.findAll();
    }

    @Override
    public CustomerContact save(CustomerContact customerContact) {
        if (customerContact == null || customerContact.getCustomer() == null) {
            throw new BadRequestException();
        }
        if (customerService.findById(customerContact.getCustomer().getId()).isEmpty()) {
            throw new BadRequestException();
        }
        customerContact.setUsed(false);
        return customerContactRepo.save(customerContact);
    }

    @Override
    public CustomerContact update(CustomerContact pContact, Integer contactId) {
        if (pContact == null || pContact.getCustomer() == null) {
            throw new ResourceNotFoundException("Customer not found!");
        }
        Optional<CustomerContact> contact = this.findById(contactId);
        if (contact.isEmpty()) {
            throw new BadRequestException("Contact not found!");
        }
        contact.get().setCode(pContact.getCode());
        contact.get().setValue(pContact.getValue());
        contact.get().setNote(pContact.getNote());
        contact.get().setIsDefault(pContact.getIsDefault());
        return customerContactRepo.save(contact.get());
    }

    @Override
    public String delete(Integer contactId) {
        Optional<CustomerContact> customerContact = this.findById(contactId);
        if (customerContact.isEmpty()) {
            throw new BadRequestException();
        }
        if (customerContact.get().isUsed()) {
            throw new DataInUseException("This contact has been used!");
        }
        customerContactRepo.deleteById(contactId);

        String contactCode = customerContact.get().getCode();
        String customerName = customerContact.get().getCustomer().getCustomerName();
        systemLogService.writeLogDelete(MODULE.SALES, ACTION.PRO_CUS_D, MasterObject.CustomerContact, "Xóa %s của khách hàng %s".formatted(contactCode, customerName), customerContact.get().getValue());

        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public List<CustomerContact> findContacts(Integer customerId) {
        if (customerService.findById(customerId).isEmpty()) {
            throw new ResourceNotFoundException("Customer not found!");
        }
        return customerContactRepo.findByCustomerId(customerId);
    }

    @Override
    public Optional<CustomerContact> findById(Integer contactId) {
        return customerContactRepo.findById(contactId);
    }

    @Override
    public CustomerContact findContactPhoneUseDefault(Integer customerId) {
        return customerContactRepo.findPhoneUseDefault(customerId);
    }

    @Override
    public CustomerContact findContactEmailUseDefault(Integer customerId) {
        return customerContactRepo.findEmailUseDefault(customerId);
    }

    @Override
    public CustomerContact findContactAddressUseDefault(Integer customerId) {
        return customerContactRepo.findAddressUseDefault(customerId);
    }

    @Override
    public CustomerContact enableContactUseDefault(Integer customerId, String contactCode, Integer contactId) {
        if (customerService.findById(customerId).isEmpty()) {
            throw new ResourceNotFoundException("Customer not found!");
        }
        if (this.findById(contactId).isEmpty()) {
            throw new ResourceNotFoundException("Customer contact not found!");
        }
        CustomerContact customerContactUsingDefault = switch (contactCode) {
            case "P" -> customerContactRepo.findPhoneUseDefault(customerId);
            case "E" -> customerContactRepo.findEmailUseDefault(customerId);
            case "A" -> customerContactRepo.findAddressUseDefault(customerId);
            default -> throw new IllegalStateException("Unexpected value: " + contactCode);
        };
        if (customerContactUsingDefault != null) {
            customerContactUsingDefault.setIsDefault("N");
            customerContactRepo.save(customerContactUsingDefault);
        }
        Optional<CustomerContact> customerContactToUseDefault = this.findById(contactId);
        if (customerContactToUseDefault.isEmpty()) {
            throw new BadRequestException();
        }
        customerContactToUseDefault.get().setIsDefault("Y");
        return this.update(customerContactToUseDefault.get(), customerContactToUseDefault.get().getId());
    }

    @Override
    public CustomerContact disableContactUnUseDefault(Integer contactId) {
        Optional<CustomerContact> customerContact = this.findById(contactId);
        if (customerContact.isEmpty()) {
            throw new BadRequestException();
        }
        customerContact.get().setIsDefault("N");
        return this.update(customerContact.get(), customerContact.get().getId());
    }
}