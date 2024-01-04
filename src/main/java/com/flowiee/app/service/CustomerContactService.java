package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.CustomerContact;

import java.util.List;

public interface CustomerContactService extends BaseService<CustomerContact> {
    List<CustomerContact> findAll();

    List<CustomerContact> findByCustomerId(Integer customerId);

    String setContactUseDefault(Integer customerId, String contactCode, Integer contactId);

    String setContactUnUseDefault(Integer contactId);

    CustomerContact findPhoneUseDefault(Integer customerId);

    CustomerContact findEmailUseDefault(Integer customerId);

    CustomerContact findAddressUseDefault(Integer customerId);
}