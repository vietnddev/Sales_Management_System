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
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(CustomerContact entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0 || this.findByCustomerId(entityId) == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        customerContactRepository.save(entity);
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
            List<CustomerContact> listData = customerContactRepository.findByCustomerId(customerId);
            for (CustomerContact c : listData) {
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
            return listData;
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
    public String findPhoneUseDefault(Integer customerId) {
        CustomerContact customerContact = customerContactRepository.findPhoneUseDefault(customerId);
        if (customerContact != null) {
            return customerContact.getValue();
        }
        return null;
    }

    @Override
    public String findEmailUseDefault(Integer customerId) {
        CustomerContact customerContact = customerContactRepository.findEmailUseDefault(customerId);
        if (customerContact != null) {
            return customerContact.getValue();
        }
        return null;
    }

    @Override
    public String findAddressUseDefault(Integer customerId) {
        CustomerContact customerContact = customerContactRepository.findAddressUseDefault(customerId);
        if (customerContact != null) {
            return customerContact.getValue();
        }
        return null;
    }
}