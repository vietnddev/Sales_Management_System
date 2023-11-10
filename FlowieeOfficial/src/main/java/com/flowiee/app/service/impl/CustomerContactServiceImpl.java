package com.flowiee.app.service.impl;

import com.flowiee.app.common.utils.TagName;
import com.flowiee.app.entity.CustomerContact;
import com.flowiee.app.repository.product.CustomerContactRepository;
import com.flowiee.app.service.product.CustomerContactService;

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
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        customerContactRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(CustomerContact entity, Integer entityId) {
        if (entity == null || entityId == null || entityId <= 0 || this.findByCustomerId(entityId) == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        entity.setId(entityId);
        customerContactRepository.save(entity);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer entityId) {
        if (entityId == null || entityId <= 0) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        CustomerContact customerContact = this.findById(entityId);
        if (customerContact == null) {
            return TagName.SERVICE_RESPONSE_FAIL;
        }
        customerContactRepository.deleteById(entityId);
        return TagName.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public List<CustomerContact> findByCustomerId(Integer customerId) {
        List<CustomerContact> listData = new ArrayList<>();
        if (customerId != null && customerId > 0) {
            listData = customerContactRepository.findByCustomerId(customerId);
        }
        return listData;
    }
}