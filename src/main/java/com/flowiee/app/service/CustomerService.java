package com.flowiee.app.service;

import com.flowiee.app.dto.CustomerDTO;
import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.CustomerContact;

import java.util.Date;
import java.util.List;

public interface CustomerService {
    List<Customer> findAllCustomer();

    List<CustomerDTO> findAllCustomer(String name, String sex, Date birthday, String phone, String email, String address);

    List<Customer> findCustomerNewInMonth();

    Customer findCustomerById(Integer customerId);

    String saveCustomer(Customer Rcustomer);

    String saveContact(CustomerContact customerContact, Integer customerId);

    String updateCustomer(Customer customer, Integer customerId);

    String updateContact(CustomerContact customerContact, Integer contactId);

    String deleteCustomer(Integer customerId);

    String deleteContact(Integer contactId);

    List<CustomerContact> findContactsByCustomerId(Integer customerId);

    CustomerContact findContactById(Integer contactId);

    CustomerContact findContactPhoneUseDefault(Integer customerId);

    CustomerContact findContactEmailUseDefault(Integer customerId);

    CustomerContact findContactAddressUseDefault(Integer customerId);

    String setContactUseDefault(Integer customerId, String type, Integer contactId);

    String setContactUnUseDefault(Integer contactId);
}