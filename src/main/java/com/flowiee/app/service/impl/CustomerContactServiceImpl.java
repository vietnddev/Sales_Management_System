package com.flowiee.app.service.impl;

import com.flowiee.app.entity.CustomerContact;
import com.flowiee.app.repository.CustomerContactRepository;
import com.flowiee.app.service.CustomerContactService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerContactServiceImpl implements CustomerContactService {
    @Autowired
    private CustomerContactRepository customerContactRepository;

    @Override
    public List<CustomerContact> findAll() {
        return customerContactRepository.findAll();
    }

    @Override
    public CustomerContact findById(Integer entityId) {
        return customerContactRepository.findById(entityId).orElse(null);
    }

    @Override
    public String save(CustomerContact entity) {
        if (entity == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        customerContactRepository.save(entity);
        customerContactRepository.flush();
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(CustomerContact entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0 || this.findById(entityId) == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        customerContactRepository.save(entity);
        customerContactRepository.flush();
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        CustomerContact customerContact = this.findById(entityId);
        if (customerContact == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        customerContactRepository.deleteById(entityId);
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public List<CustomerContact> findByCustomerId(Integer customerId) {
        if (customerId != null && customerId > 0) {
            return customerContactRepository.findByCustomerId(customerId);
        }
        return null;
    }

    @Override
    public String setContactUseDefault(Integer customerId, String contactCode, Integer contactId) {
        new CustomerContact();
        CustomerContact customerContactUsingDefault = switch (contactCode) {
            case "P" -> customerContactRepository.findPhoneUseDefault(customerId);
            case "E" -> customerContactRepository.findEmailUseDefault(customerId);
            case "A" -> customerContactRepository.findAddressUseDefault(customerId);
            default -> throw new IllegalStateException("Unexpected value: " + contactCode);
        };
        if (customerContactUsingDefault != null) {
            customerContactUsingDefault.setIsDefault("N");
            customerContactRepository.save(customerContactUsingDefault);
        }
        CustomerContact customerContactToUseDefault = this.findById(contactId);
        customerContactToUseDefault.setIsDefault("Y");
        this.update(customerContactToUseDefault, customerContactToUseDefault.getId());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String setContactUnUseDefault(Integer contactId) {
        CustomerContact customerContact = this.findById(contactId);
        customerContact.setIsDefault("N");
        this.update(customerContact, customerContact.getId());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public CustomerContact findPhoneUseDefault(Integer customerId) {
        return customerContactRepository.findPhoneUseDefault(customerId);
    }

    @Override
    public CustomerContact findEmailUseDefault(Integer customerId) {
        return customerContactRepository.findEmailUseDefault(customerId);
    }

    @Override
    public CustomerContact findAddressUseDefault(Integer customerId) {
        return customerContactRepository.findAddressUseDefault(customerId);
    }
}