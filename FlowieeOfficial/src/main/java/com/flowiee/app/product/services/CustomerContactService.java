package com.flowiee.app.product.services;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.product.entity.CustomerContact;

import java.util.List;

public interface CustomerContactService extends BaseService<CustomerContact> {
    List<CustomerContact> findByCustomerId(Integer customerId);
}