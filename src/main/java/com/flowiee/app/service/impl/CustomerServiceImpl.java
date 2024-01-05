package com.flowiee.app.service.impl;

import com.flowiee.app.dto.CustomerDTO;
import com.flowiee.app.entity.CustomerContact;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.exception.DataInUseException;
import com.flowiee.app.model.role.SystemAction.ProductAction;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.entity.Customer;
import com.flowiee.app.repository.CustomerContactRepository;
import com.flowiee.app.repository.CustomerRepository;
import com.flowiee.app.service.CustomerContactService;
import com.flowiee.app.service.CustomerService;
import com.flowiee.app.service.OrderService;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.ErrorMessages;
import com.flowiee.app.utils.MessagesUtil;
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
    private static final String module = SystemModule.PRODUCT.name();

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerContactService customerContactService;
    @Autowired
    private CustomerContactRepository customerContactRepository;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private OrderService orderService;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<CustomerDTO> findAll(String name, String sex, Date birthday, String phone, String email, String address) {
        List<CustomerDTO> dataReturn =  new ArrayList<>();
        customerRepository.findAll(name, sex, birthday, phone, email, address).forEach(customer -> {
            CustomerDTO dto = CustomerDTO.fromCustomer(customer);
            CustomerContact phoneDefault = customerContactService.findPhoneUseDefault(customer.getId());
            CustomerContact emailDefault = customerContactService.findEmailUseDefault(customer.getId());
            CustomerContact addressDefault = customerContactService.findAddressUseDefault(customer.getId());
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
    public Customer findById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public String save(Customer customer) {
        customer.setCreatedBy(CommonUtil.getCurrentAccountId());
        Customer customerInserted = customerRepository.save(customer);
        if (customer.getPhoneDefault() != null) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomer(new Customer(customerInserted.getId()));
            customerContact.setCode("P");
            customerContact.setValue(customer.getPhoneDefault());
            customerContact.setIsDefault("Y");
            customerContact.setStatus(true);
            customerContactService.save(customerContact);
        }
        if (customer.getEmailDefault() != null) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomer(new Customer(customerInserted.getId()));
            customerContact.setCode("E");
            customerContact.setValue(customer.getEmailDefault());
            customerContact.setIsDefault("Y");
            customerContact.setStatus(true);
            customerContactService.save(customerContact);
        }
        if (customer.getAddressDefault() != null) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomer(new Customer(customerInserted.getId()));
            customerContact.setCode("A");
            customerContact.setValue(customer.getAddressDefault());
            customerContact.setIsDefault("Y");
            customerContact.setStatus(true);
            customerContactService.save(customerContact);
        }
        systemLogService.writeLog(module, ProductAction.PRO_CUSTOMER_CREATE.name(), "Thêm mới khách hàng: " + customer.toString());
        logger.info(ProductServiceImpl.class.getName() + ": Thêm mới khách hàng " + customer.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Transactional
    @Override
    public String update(Customer customer, Integer customerId) {
        if (customer == null || customerId <= 0 || this.findById(customerId) == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        customer.setId(customerId);
        customerRepository.save(customer);

        CustomerContact phoneDefault = null;
        CustomerContact emailDefault = null;
        CustomerContact addressDefault = null;
        if (customer.getPhoneDefault() != null || customer.getEmailDefault() != null || customer.getAddressDefault() != null) {
            List<CustomerContact> contacts = customerContactService.findByCustomerId(customerId);
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
                customerContactService.save(phoneDefault);
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
                customerContactService.save(emailDefault);
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
                customerContactService.save(addressDefault);
            }
        }
        systemLogService.writeLog(module, ProductAction.PRO_CUSTOMER_UPDATE.name(), "Cập nhật thông tin khách hàng: " + customer.toString());
        logger.info(ProductServiceImpl.class.getName() + ": Cập nhật khách hàng " + customer.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer id) {
        Customer customer = this.findById(id);
        if (id <= 0 || customer == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        if (!orderService.findByCustomer(id).isEmpty()) {
            throw new DataInUseException(ErrorMessages.ERROR_LOCKED);
        }
        customerRepository.deleteById(id);
        systemLogService.writeLog(module, ProductAction.PRO_CUSTOMER_DELETE.name(), "Xóa khách hàng: " + customer.toString());
        logger.info(ProductServiceImpl.class.getName() + ": Xóa khách hàng " + customer.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}