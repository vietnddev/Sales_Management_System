package com.flowiee.sms.service.impl;

import com.flowiee.sms.model.ACTION;
import com.flowiee.sms.model.MODULE;
import com.flowiee.sms.model.dto.CustomerDTO;
import com.flowiee.sms.entity.CustomerContact;
import com.flowiee.sms.core.exception.BadRequestException;
import com.flowiee.sms.core.exception.DataInUseException;
import com.flowiee.sms.entity.Customer;
import com.flowiee.sms.repository.CustomerContactRepository;
import com.flowiee.sms.repository.CustomerRepository;
import com.flowiee.sms.service.CustomerService;
import com.flowiee.sms.service.OrderService;
import com.flowiee.sms.service.SystemLogService;

import com.flowiee.sms.utils.CommonUtils;
import com.flowiee.sms.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private static final String module = MODULE.PRODUCT.name();

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerContactRepository customerContactRepository;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private OrderService orderService;

    @Override
    public List<CustomerDTO> findAllCustomer() {
        return this.findAllCustomer(null, null, null, null, null, null);
    }

    @Override
    public Page<Customer> findAllCustomer(Integer pageSize, Integer pageNum) {
        Pageable pageable;
        if (pageSize == null || pageNum == null) {
            pageable = Pageable.unpaged();
        } else {
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("customerName").descending());
        }
        return customerRepository.findAll(pageable);
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
    public CustomerDTO saveCustomer(CustomerDTO dto) {
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
            this.saveContact(customerContact, customerInserted.getId());
        }
        if (dto.getEmailDefault() != null) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomer(new Customer(customerInserted.getId()));
            customerContact.setCode("E");
            customerContact.setValue(dto.getEmailDefault());
            customerContact.setIsDefault("Y");
            customerContact.setStatus(true);
            this.saveContact(customerContact, customerInserted.getId());
        }
        if (dto.getAddressDefault() != null) {
            CustomerContact customerContact = new CustomerContact();
            customerContact.setCustomer(new Customer(customerInserted.getId()));
            customerContact.setCode("A");
            customerContact.setValue(dto.getAddressDefault());
            customerContact.setIsDefault("Y");
            customerContact.setStatus(true);
            this.saveContact(customerContact, customerInserted.getId());
        }
        systemLogService.writeLog(module, ACTION.PRO_CUSTOMER_CREATE.name(), "Thêm mới khách hàng: " + customer.toString());
        logger.info(ProductServiceImpl.class.getName() + ": Thêm mới khách hàng " + customer.toString());
        return CustomerDTO.fromCustomer(customerInserted);
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
    public CustomerDTO updateCustomer(CustomerDTO customer, Integer customerId) {
        if (customer == null || customerId <= 0 || this.findCustomerById(customerId) == null) {
            throw new BadRequestException();
        }
        Customer customerToUpdate = Customer.fromCustomerDTO(customer);
        customerToUpdate.setId(customerId);
        Customer customerUpdated = customerRepository.save(customerToUpdate);

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
        systemLogService.writeLog(module, ACTION.PRO_CUSTOMER_UPDATE.name(), "Cập nhật thông tin khách hàng: " + customer.toString());
        logger.info(ProductServiceImpl.class.getName() + ": Cập nhật khách hàng " + customer.toString());
        return CustomerDTO.fromCustomer(customerUpdated);
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
            throw new BadRequestException();
        }
        if (!orderService.findOrdersByCustomerId(id).isEmpty()) {
            throw new DataInUseException(MessageUtils.ERROR_DATA_LOCKED);
        }
        customerRepository.deleteById(id);
        systemLogService.writeLog(module, ACTION.PRO_CUSTOMER_DELETE.name(), "Xóa khách hàng: " + customer.getCustomerName());
        logger.info(ProductServiceImpl.class.getName() + ": Xóa khách hàng " + customer.toString());
        return MessageUtils.DELETE_SUCCESS;
    }

    @Override
    public String deleteContact(Integer contactId) {
        CustomerContact customerContact = this.findContactById(contactId);
        if (customerContact == null) {
            throw new BadRequestException();
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

    @Override
    public List<CustomerDTO> convertToDTOAndSetContactDefault(Page<Customer> customers) {
        List<CustomerDTO> dataReturn =  new ArrayList<>();
        customers.getContent().forEach(customer -> {
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
}