package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.model.ACTION;
import com.flowiee.pms.model.MODULE;
import com.flowiee.pms.model.dto.CustomerDTO;
import com.flowiee.pms.entity.sales.CustomerContact;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.entity.sales.Customer;
import com.flowiee.pms.repository.sales.CustomerContactRepository;
import com.flowiee.pms.repository.sales.CustomerRepository;
import com.flowiee.pms.service.sales.CustomerContactService;
import com.flowiee.pms.service.sales.CustomerService;
import com.flowiee.pms.service.sales.OrderService;
import com.flowiee.pms.service.system.SystemLogService;

import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerContactRepository customerContactRepository;
    @Autowired
    private CustomerContactService customerContactService;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private OrderService orderService;

    @Override
    public List<CustomerDTO> findAll() {
        return this.findAll(-1, -1, null, null, null, null, null, null).getContent();
    }

    @Override
    public Page<CustomerDTO> findAll(int pageSize, int pageNum, String name, String sex, Date birthday, String phone, String email, String address) {
        Pageable pageable = Pageable.unpaged();
        if (pageSize >= 0 && pageNum >= 0) {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("customerName").ascending());
        }
        Page<Customer> customers = customerRepository.findAll(name, sex, birthday, phone, email, address, pageable);
        List<CustomerDTO> customerDTOs = CustomerDTO.fromCustomers(customers.getContent());
        this.setContactDefault(customerDTOs);
        return new PageImpl<>(customerDTOs, pageable, customerDTOs.size());
    }

    @Override
    public List<CustomerDTO> findCustomerNewInMonth() {
        List<CustomerDTO> customerDTOs = CustomerDTO.fromCustomers(customerRepository.findCustomerNewInMonth());
        this.setContactDefault(customerDTOs);
        return customerDTOs;
    }

    @Override
    public Optional<CustomerDTO> findById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            CustomerDTO customerDTO = CustomerDTO.fromCustomer(customer.get());
            this.setContactDefault(List.of(customerDTO));
            return Optional.of(customerDTO);
        }
        throw new BadRequestException();
    }

    @Transactional
    @Override
    public CustomerDTO save(CustomerDTO dto) {
        if (dto == null) {
            throw new NotFoundException("Customer not found!");
        }
        Customer customer = Customer.fromCustomerDTO(dto);
        customer.setCreatedBy(CommonUtils.getUserPrincipal().getId());
        Customer customerInserted = customerRepository.save(customer);
        if (dto.getPhoneDefault() != null) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomer(new Customer(customerInserted.getId()));
            customerContact.setCode("P");
            customerContact.setValue(dto.getPhoneDefault());
            customerContact.setIsDefault("Y");
            customerContact.setStatus(true);
            customerContactService.save(customerContact);
        }
        if (dto.getEmailDefault() != null) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomer(new Customer(customerInserted.getId()));
            customerContact.setCode("E");
            customerContact.setValue(dto.getEmailDefault());
            customerContact.setIsDefault("Y");
            customerContact.setStatus(true);
            customerContactService.save(customerContact);
        }
        if (dto.getAddressDefault() != null) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomer(new Customer(customerInserted.getId()));
            customerContact.setCode("A");
            customerContact.setValue(dto.getAddressDefault());
            customerContact.setIsDefault("Y");
            customerContact.setStatus(true);
            customerContactService.save(customerContact);
        }
        systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_CUS_C.name(), "Thêm mới khách hàng: " + customer.toString());
        logger.info("Create customer {}", customer.toString());
        return CustomerDTO.fromCustomer(customerInserted);
    }

    @Transactional
    @Override
    public CustomerDTO update(CustomerDTO customer, Integer customerId) {
        if (customer == null || customerId <= 0 || this.findById(customerId).isEmpty()) {
            throw new BadRequestException();
        }
        Customer customerToUpdate = Customer.fromCustomerDTO(customer);
        customerToUpdate.setId(customerId);
        Customer customerUpdated = customerRepository.save(customerToUpdate);

        CustomerContact phoneDefault = null;
        CustomerContact emailDefault = null;
        CustomerContact addressDefault = null;
        if (customer.getPhoneDefault() != null || customer.getEmailDefault() != null || customer.getAddressDefault() != null) {
            List<CustomerContact> contacts = customerContactService.findContacts(customerId);
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
        systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_CUS_U.name(), "Cập nhật thông tin khách hàng: " + customer.toString());
        logger.info("Update customer info {}", customer.toString());
        return CustomerDTO.fromCustomer(customerUpdated);
    }

    @Override
    public String delete(Integer id) {
        if (this.findById(id).isEmpty()) {
            throw new BadRequestException("Customer not found!");
        }
        if (!orderService.findAll().isEmpty()) {
            throw new DataInUseException(MessageUtils.ERROR_DATA_LOCKED);
        }
        customerRepository.deleteById(id);
        systemLogService.writeLog(MODULE.PRODUCT.name(), ACTION.PRO_CUS_D.name(), "Xóa khách hàng id=" + id);
        logger.info("Deleted customer id={}", id);
        return MessageUtils.DELETE_SUCCESS;
    }

    private void setContactDefault(List<CustomerDTO> customerDTOs) {
        for (CustomerDTO c : customerDTOs) {
            CustomerContact phoneDefault = customerContactService.findContactPhoneUseDefault(c.getId());
            CustomerContact emailDefault = customerContactService.findContactEmailUseDefault(c.getId());
            CustomerContact addressDefault = customerContactService.findContactAddressUseDefault(c.getId());
            c.setPhoneDefault(phoneDefault != null ? phoneDefault.getValue() : "");
            c.setEmailDefault(emailDefault != null ? emailDefault.getValue() : "");
            c.setAddressDefault(addressDefault != null ? addressDefault.getValue() : "");
        }
    }
}