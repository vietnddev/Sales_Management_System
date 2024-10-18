package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.CustomerContact;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.repository.sales.CustomerContactRepository;
import com.flowiee.pms.repository.sales.CustomerRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.CustomerContactService;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.utils.constants.MasterObject;
import com.flowiee.pms.utils.constants.MessageCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomerContactServiceImpl extends BaseService implements CustomerContactService {
    CustomerContactRepository mvCustomerContactRepository;
    CustomerRepository mvCustomerRepository;

    @Override
    public List<CustomerContact> findAll() {
        return mvCustomerContactRepository.findAll();
    }

    @Override
    public CustomerContact save(CustomerContact customerContact) {
        if (customerContact == null || customerContact.getCustomer() == null) {
            throw new BadRequestException();
        }
        if (mvCustomerRepository.findById(customerContact.getCustomer().getId()).isEmpty()) {
            throw new BadRequestException("Customer contact not found!");
        }
        customerContact.setUsed(false);
        return mvCustomerContactRepository.save(customerContact);
    }

    @Override
    public CustomerContact update(CustomerContact pContact, Long contactId) {
        if (pContact == null || pContact.getCustomer() == null) {
            throw new ResourceNotFoundException("Customer not found!");
        }
        Optional<CustomerContact> contactOpt = this.findById(contactId);
        if (contactOpt.isEmpty()) {
            throw new BadRequestException("Contact not found!");
        }
        CustomerContact contact = contactOpt.get();
        contact.setCode(pContact.getCode());
        contact.setValue(pContact.getValue());
        contact.setNote(pContact.getNote());
        contact.setIsDefault(pContact.getIsDefault());
        return mvCustomerContactRepository.save(contact);
    }

    @Override
    public String delete(Long contactId) {
        Optional<CustomerContact> customerContact = this.findById(contactId);
        if (customerContact.isEmpty()) {
            throw new BadRequestException();
        }
        if (customerContact.get().isUsed()) {
            throw new DataInUseException("This contact has been used!");
        }
        mvCustomerContactRepository.deleteById(contactId);

        String contactCode = customerContact.get().getCode();
        String customerName = customerContact.get().getCustomer().getCustomerName();
        systemLogService.writeLogDelete(MODULE.SALES, ACTION.PRO_CUS_D, MasterObject.CustomerContact, "Xóa %s của khách hàng %s".formatted(contactCode, customerName), customerContact.get().getValue());

        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    @Override
    public List<CustomerContact> findContacts(Long customerId) {
        if (mvCustomerRepository.findById(customerId).isEmpty()) {
            throw new ResourceNotFoundException("Customer not found!");
        }
        return mvCustomerContactRepository.findByCustomerId(customerId);
    }

    @Override
    public Optional<CustomerContact> findById(Long contactId) {
        return mvCustomerContactRepository.findById(contactId);
    }

    @Override
    public CustomerContact findContactPhoneUseDefault(Long customerId) {
        return mvCustomerContactRepository.findPhoneUseDefault(customerId);
    }

    @Override
    public CustomerContact findContactEmailUseDefault(Long customerId) {
        return mvCustomerContactRepository.findEmailUseDefault(customerId);
    }

    @Override
    public CustomerContact findContactAddressUseDefault(Long customerId) {
        return mvCustomerContactRepository.findAddressUseDefault(customerId);
    }

    @Override
    public CustomerContact enableContactUseDefault(Long customerId, String contactCode, Long contactId) {
        if (mvCustomerRepository.findById(customerId).isEmpty()) {
            throw new ResourceNotFoundException("Customer not found!");
        }
        if (this.findById(contactId).isEmpty()) {
            throw new ResourceNotFoundException("Customer contact not found!");
        }
        CustomerContact customerContactUsingDefault = switch (contactCode) {
            case "P" -> mvCustomerContactRepository.findPhoneUseDefault(customerId);
            case "E" -> mvCustomerContactRepository.findEmailUseDefault(customerId);
            case "A" -> mvCustomerContactRepository.findAddressUseDefault(customerId);
            default -> throw new IllegalStateException("Unexpected value: " + contactCode);
        };
        if (customerContactUsingDefault != null) {
            customerContactUsingDefault.setIsDefault("N");
            mvCustomerContactRepository.save(customerContactUsingDefault);
        }
        Optional<CustomerContact> customerContactToUseDefault = this.findById(contactId);
        if (customerContactToUseDefault.isEmpty()) {
            throw new BadRequestException();
        }
        customerContactToUseDefault.get().setIsDefault("Y");
        return this.update(customerContactToUseDefault.get(), customerContactToUseDefault.get().getId());
    }

    @Override
    public CustomerContact disableContactUnUseDefault(Long contactId) {
        Optional<CustomerContact> customerContact = this.findById(contactId);
        if (customerContact.isEmpty()) {
            throw new BadRequestException();
        }
        customerContact.get().setIsDefault("N");
        return this.update(customerContact.get(), customerContact.get().getId());
    }
}