package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.exception.NotFoundException;
import com.flowiee.pms.utils.constants.ACTION;
import com.flowiee.pms.utils.constants.MODULE;
import com.flowiee.pms.model.PurchaseHistory;
import com.flowiee.pms.model.dto.CustomerDTO;
import com.flowiee.pms.entity.sales.CustomerContact;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.exception.DataInUseException;
import com.flowiee.pms.entity.sales.Customer;
import com.flowiee.pms.repository.sales.CustomerContactRepository;
import com.flowiee.pms.repository.sales.CustomerRepository;
import com.flowiee.pms.repository.sales.OrderRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.CustomerContactService;
import com.flowiee.pms.service.sales.CustomerService;
import com.flowiee.pms.service.sales.OrderService;

import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl extends BaseService implements CustomerService {
    private static final String mainObjectName = "Customer";

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerContactRepository customerContactRepository;
    @Autowired
    private CustomerContactService customerContactService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

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
        customer.setCreatedBy(dto.getCreatedBy() != null ? dto.getCreatedBy() : CommonUtils.getUserPrincipal().getId());
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
        systemLogService.writeLogCreate(MODULE.PRODUCT.name(), ACTION.PRO_CUS_C.name(), mainObjectName, "Thêm mới khách hàng", customer.toString());
        logger.info("Create customer: {}", customer.toString());
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
        systemLogService.writeLogUpdate(MODULE.PRODUCT.name(), ACTION.PRO_CUS_U.name(), mainObjectName, "Cập nhật thông tin khách hàng", customer.toString());
        logger.info("Update customer info {}", customer.toString());
        return CustomerDTO.fromCustomer(customerUpdated);
    }

    @Override
    public String delete(Integer id) {
        Optional<CustomerDTO> customer = this.findById(id);
        if (customer.isEmpty()) {
            throw new BadRequestException("Customer not found!");
        }
        if (!orderService.findAll().isEmpty()) {
            throw new DataInUseException(MessageUtils.ERROR_DATA_LOCKED);
        }
        customerRepository.deleteById(id);
        systemLogService.writeLogDelete(MODULE.PRODUCT.name(), ACTION.PRO_CUS_D.name(), mainObjectName, "Xóa khách hàng", customer.get().getCustomerName());
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

    @Override
    public List<PurchaseHistory> findPurchaseHistory(Integer customerId, Integer year, Integer month) {
        List<PurchaseHistory> purchaseHistories = new ArrayList<>();
        if (year == null) {
            year = LocalDateTime.now().getYear();
        }
        List<Object[]> purchaseHistoriesRawValue = orderRepository.findPurchaseHistory(customerId, year, month);
        //col 0 -> year
        //col 1 -> month
        //col 2 -> purchase qty
        //col 3 -> average value
        for (Object[] data : purchaseHistoriesRawValue) {
            PurchaseHistory ph = new PurchaseHistory();
            ph.setCustomerId(customerId);
            ph.setYear(year);
            ph.setMonth(Integer.parseInt(String.valueOf(data[1])));
            ph.setPurchaseQty(Integer.parseInt(String.valueOf(data[2])));
            ph.setOrderAvgValue(new BigDecimal(String.valueOf(data[3])));
            purchaseHistories.add(ph);
        }
        return purchaseHistories;
    }
}