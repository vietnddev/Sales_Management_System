package com.flowiee.app.service.impl;

import com.flowiee.app.dto.CustomerDTO;
import com.flowiee.app.entity.CustomerContact;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.exception.DataInUseException;
import com.flowiee.app.entity.Customer;
import com.flowiee.app.repository.CustomerContactRepository;
import com.flowiee.app.repository.CustomerRepository;
import com.flowiee.app.service.CustomerService;
import com.flowiee.app.service.OrderService;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private static final String module = AppConstants.SYSTEM_MODULE.PRODUCT.name();

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerContactRepository customerContactRepository;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private OrderService orderService;

//    @Override
//    public List<Customer> findAllCustomer() {
//        return customerRepository.findAll();
//    }

    @Override
    public List<CustomerDTO> findAllCustomer() {
        return this.findAllCustomer(null, null, null, null, null, null);
    }

    @Override
    public List<CustomerDTO> findAllCustomer(String name, String sex, Date birthday, String phone, String email, String address) {
        List<CustomerDTO> dataReturn =  new ArrayList<>();
        customerRepository.findAll(name, sex, birthday, phone, email, address).forEach(customer -> {
            CustomerDTO dto = CustomerDTO.fromCustomer(customer);
            CustomerContact phoneDefault = this.findContactPhoneUseDefault(customer.getId());
            CustomerContact emailDefault = this.findContactEmailUseDefault(customer.getId());
            CustomerContact addressDefault = this.findContactAddressUseDefault(customer.getId());
            dto.setPhoneDefault(phoneDefault != null ? phoneDefault.getValue() : "");
            dto.setEmailDefault(emailDefault != null ? emailDefault.getValue() : "");
            dto.setAddressDefault(addressDefault != null ? addressDefault.getValue() : "");
            dataReturn.add(dto);
        });
        return dataReturn;
    }

    @Override
    public List<Customer> findCustomerNewInMonth() {
        return customerRepository.findCustomerNewInMonth();
    }

    @Override
    public Customer findCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public String saveCustomer(Customer customer) {
        customer.setCreatedBy(CommonUtils.getCurrentAccountId());
        Customer customerInserted = customerRepository.save(customer);
        if (customer.getPhoneDefault() != null) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomer(new Customer(customerInserted.getId()));
            customerContact.setCode("P");
            customerContact.setValue(customer.getPhoneDefault());
            customerContact.setIsDefault("Y");
            customerContact.setStatus(true);
            this.saveContact(customerContact, customerInserted.getId());
        }
        if (customer.getEmailDefault() != null) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomer(new Customer(customerInserted.getId()));
            customerContact.setCode("E");
            customerContact.setValue(customer.getEmailDefault());
            customerContact.setIsDefault("Y");
            customerContact.setStatus(true);
            this.saveContact(customerContact, customerInserted.getId());
        }
        if (customer.getAddressDefault() != null) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomer(new Customer(customerInserted.getId()));
            customerContact.setCode("A");
            customerContact.setValue(customer.getAddressDefault());
            customerContact.setIsDefault("Y");
            customerContact.setStatus(true);
            this.saveContact(customerContact, customerInserted.getId());
        }
        systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_CUSTOMER_CREATE.name(), "Thêm mới khách hàng: " + customer.toString());
        logger.info(ProductServiceImpl.class.getName() + ": Thêm mới khách hàng " + customer.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public CustomerContact saveContact(CustomerContact customerContact, Integer customerId) {
        if (customerContact == null) {
            throw new BadRequestException();
        }
        return customerContactRepository.save(customerContact);
    }

    @Transactional
    @Override
    public String updateCustomer(Customer customer, Integer customerId) {
        if (customer == null || customerId <= 0 || this.findCustomerById(customerId) == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        customer.setId(customerId);
        customerRepository.save(customer);

        CustomerContact phoneDefault = null;
        CustomerContact emailDefault = null;
        CustomerContact addressDefault = null;
        if (customer.getPhoneDefault() != null || customer.getEmailDefault() != null || customer.getAddressDefault() != null) {
            List<CustomerContact> contacts = this.findContactsByCustomerId(customerId);
            for (CustomerContact contact : contacts) {
                if ("P".equals(contact.getCode()) && "Y".equals(contact.getIsDefault()) && contact.isStatus()) {
                    phoneDefault = contact;
                }
                if ("E".equals(contact.getCode()) && "Y".equals(contact.getIsDefault()) && contact.isStatus()) {
                    emailDefault = contact;
                }
                if ("A".equals(contact.getCode()) && "Y".equals(contact.getIsDefault()) && contact.isStatus()) {
                    addressDefault = contact;
                }
            }

            if (customer.getPhoneDefault() != null && phoneDefault != null) {
                phoneDefault.setValue(customer.getPhoneDefault());
                //customerContactService.update(phoneDefault, customerId);
                customerContactRepository.save(phoneDefault);
            } else if (customer.getPhoneDefault() != null && !customer.getPhoneDefault().isEmpty()) {
                phoneDefault = new CustomerContact();
                phoneDefault.setCustomer(new Customer(customerId));
                phoneDefault.setCode("P");
                phoneDefault.setValue(customer.getPhoneDefault());
                phoneDefault.setIsDefault("Y");
                phoneDefault.setStatus(true);
                this.saveContact(phoneDefault, customerId);
            }

            if (customer.getEmailDefault() != null && emailDefault != null) {
                emailDefault.setValue(customer.getEmailDefault());
                //customerContactService.update(emailDefault, customerId);
                customerContactRepository.save(emailDefault);
            } else if (customer.getEmailDefault() != null && !customer.getEmailDefault().isEmpty()) {
                emailDefault = new CustomerContact();
                emailDefault.setCustomer(new Customer(customerId));
                emailDefault.setCode("E");
                emailDefault.setValue(customer.getEmailDefault());
                emailDefault.setIsDefault("Y");
                emailDefault.setStatus(true);
                this.saveContact(emailDefault, customerId);
            }

            if (customer.getAddressDefault() != null && addressDefault != null) {
                addressDefault.setValue(customer.getAddressDefault());
                //customerContactService.update(addressDefault, customerId);
                customerContactRepository.save(addressDefault);
            } else if (customer.getAddressDefault() != null && !customer.getAddressDefault().isEmpty()) {
                addressDefault = new CustomerContact();
                addressDefault.setCustomer(new Customer(customerId));
                addressDefault.setCode("A");
                addressDefault.setValue(customer.getAddressDefault());
                addressDefault.setIsDefault("Y");
                addressDefault.setStatus(true);
                this.saveContact(addressDefault, customerId);
            }
        }
        systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_CUSTOMER_UPDATE.name(), "Cập nhật thông tin khách hàng: " + customer.toString());
        logger.info(ProductServiceImpl.class.getName() + ": Cập nhật khách hàng " + customer.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public CustomerContact updateContact(CustomerContact customerContact, Integer contactId) {
        if (customerContact == null || contactId == null || contactId <= 0 || this.findContactById(contactId) == null) {
            throw new BadRequestException();
        }
        customerContact.setId(contactId);
        return customerContactRepository.save(customerContact);
    }

    @Override
    public String deleteCustomer(Integer id) {
        Customer customer = this.findCustomerById(id);
        if (id <= 0 || customer == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        if (!orderService.findOrdersByCustomerId(id).isEmpty()) {
            throw new DataInUseException(MessageUtils.ERROR_LOCKED);
        }
        customerRepository.deleteById(id);
        systemLogService.writeLog(module, AppConstants.PRODUCT_ACTION.PRO_CUSTOMER_DELETE.name(), "Xóa khách hàng: " + customer.toString());
        logger.info(ProductServiceImpl.class.getName() + ": Xóa khách hàng " + customer.toString());
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public String deleteContact(Integer contactId) {
        if (contactId == null || contactId <= 0) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        CustomerContact customerContact = this.findContactById(contactId);
        if (customerContact == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        customerContactRepository.deleteById(contactId);
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public List<CustomerContact> findContactsByCustomerId(Integer customerId) {
        List<CustomerContact> contacts = new ArrayList<>();
        if (customerId != null && customerId > 0) {
            contacts = customerContactRepository.findByCustomerId(customerId);
        }
        return contacts;
    }

    @Override
    public CustomerContact findContactById(Integer contactId) {
        return customerContactRepository.findById(contactId).orElse(null);
    }

    @Override
    public CustomerContact findContactPhoneUseDefault(Integer customerId) {
        return customerContactRepository.findPhoneUseDefault(customerId);
    }

    @Override
    public CustomerContact findContactEmailUseDefault(Integer customerId) {
        return customerContactRepository.findEmailUseDefault(customerId);
    }

    @Override
    public CustomerContact findContactAddressUseDefault(Integer customerId) {
        return customerContactRepository.findAddressUseDefault(customerId);
    }

    @Override
    public CustomerContact setContactUseDefault(Integer customerId, String contactCode, Integer contactId) {
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
        CustomerContact customerContactToUseDefault = this.findContactById(contactId);
        customerContactToUseDefault.setIsDefault("Y");
        return this.updateContact(customerContactToUseDefault, customerContactToUseDefault.getId());
    }

    @Override
    public CustomerContact setContactUnUseDefault(Integer contactId) {
        CustomerContact customerContact = this.findContactById(contactId);
        customerContact.setIsDefault("N");
        return this.updateContact(customerContact, customerContact.getId());
    }
}