package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.entity.CustomerContact;

import java.util.List;

public interface CustomerContactService extends BaseService<CustomerContact> {
    List<CustomerContact> findByCustomerId(Integer customerId);

    String setContactUseDefault(Integer customerId, String contactCode, Integer contactId);

    String setContactUnUseDefault(Integer contactId);

    String findPhoneUseDefault(Integer customerId);

    String findEmailUseDefault(Integer customerId);

    String findAddressUseDefault(Integer customerId);
}