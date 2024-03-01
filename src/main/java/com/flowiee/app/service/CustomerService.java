package com.flowiee.app.service;

import com.flowiee.app.model.dto.CustomerDTO;
import com.flowiee.app.entity.Customer;
import com.flowiee.app.entity.CustomerContact;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface CustomerService {
    //List<Customer> findAllCustomer();

    List<CustomerDTO> findAllCustomer();

    Page<Customer> findAllCustomer(Integer pageSize, Integer pageNum);

    List<CustomerDTO> findAllCustomer(String name, String sex, Date birthday, String phone, String email, String address);

    List<Customer> findCustomerNewInMonth();

    Customer findCustomerById(Integer customerId);

    Customer saveCustomer(Customer Rcustomer);

    CustomerContact saveContact(CustomerContact customerContact, Integer customerId);

    Customer updateCustomer(Customer customer, Integer customerId);

    CustomerContact updateContact(CustomerContact customerContact, Integer contactId);

    String deleteCustomer(Integer customerId);

    String deleteContact(Integer contactId);

    List<CustomerContact> findContactsByCustomerId(Integer customerId);

    CustomerContact findContactById(Integer contactId);

    CustomerContact findContactPhoneUseDefault(Integer customerId);

    CustomerContact findContactEmailUseDefault(Integer customerId);

    CustomerContact findContactAddressUseDefault(Integer customerId);

    CustomerContact setContactUseDefault(Integer customerId, String type, Integer contactId);

    CustomerContact setContactUnUseDefault(Integer contactId);

    List<CustomerDTO> convertToDTOAndSetContactDefault(Page<Customer> customers);
}