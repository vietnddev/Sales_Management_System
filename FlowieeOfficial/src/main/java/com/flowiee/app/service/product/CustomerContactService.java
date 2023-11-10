package com.flowiee.app.service.product;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.product.CustomerContact;

import java.util.List;

public interface CustomerContactService extends BaseService<CustomerContact> {
    List<CustomerContact> findByCustomerId(Integer customerId);
}