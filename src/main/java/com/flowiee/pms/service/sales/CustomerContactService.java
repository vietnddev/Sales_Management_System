package com.flowiee.pms.service.sales;

import com.flowiee.pms.base.BaseService;
import com.flowiee.pms.entity.sales.CustomerContact;

import java.util.List;

public interface CustomerContactService extends BaseService<CustomerContact> {
    List<CustomerContact> findContacts(Integer customerId);

    CustomerContact findContactPhoneUseDefault(Integer customerId);

    CustomerContact findContactEmailUseDefault(Integer customerId);

    CustomerContact findContactAddressUseDefault(Integer customerId);

    CustomerContact enableContactUseDefault(Integer customerId, String type, Integer contactId);

    CustomerContact disableContactUnUseDefault(Integer contactId);
}