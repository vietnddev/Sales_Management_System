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
        return customerContactRepository.findById(entityId).get();
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
        List<CustomerContact> listData = new ArrayList<>();
        if (customerId != null && customerId > 0) {
            listData = customerContactRepository.findByCustomerId(customerId);
        }
        return listData;
    }

    @Override
    public String setContactUseDefault(Integer customerId, String contactCode, Integer contactId) {
        CustomerContact customerContactUsingDefault = new CustomerContact();
        if (contactCode.equals("PHONE")) {
            customerContactUsingDefault = customerContactRepository.findPhoneUseDefault(customerId);
        } else if (contactCode.equals("EMAIL")) {
            customerContactUsingDefault = customerContactRepository.findEmailUseDefault(customerId);
        } else if (contactCode.equals("ADDRESS")) {
            customerContactUsingDefault = customerContactRepository.findAddressUseDefault(customerId);
        }
        if (customerContactUsingDefault != null) {
            customerContactUsingDefault.setDefault(false);
            customerContactRepository.save(customerContactUsingDefault);
        }
        CustomerContact customerContactToUseDefault = this.findById(contactId);
        customerContactToUseDefault.setDefault(true);
        this.update(customerContactToUseDefault, customerContactToUseDefault.getId());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String setContactUnUseDefault(Integer contactId) {
        CustomerContact customerContact = this.findById(contactId);
        customerContact.setDefault(false);
        this.update(customerContact, customerContact.getId());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String findPhoneUseDefault(Integer customerId) {
        return customerContactRepository.findPhoneUseDefault(customerId).getValue();
    }

    @Override
    public String findEmailUseDefault(Integer customerId) {
        return customerContactRepository.findEmailUseDefault(customerId).getValue();
    }

    @Override
    public String findAddressUseDefault(Integer customerId) {
        return customerContactRepository.findAddressUseDefault(customerId).getValue();
    }
}