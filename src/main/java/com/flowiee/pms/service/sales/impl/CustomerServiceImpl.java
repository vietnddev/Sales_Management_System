package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.exception.ResourceNotFoundException;
import com.flowiee.pms.utils.constants.*;
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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomerServiceImpl extends BaseService implements CustomerService {
    OrderService mvOrderService;
    OrderRepository mvOrderRepository;
    CustomerRepository mvCustomerRepository;
    CustomerContactService mvCustomerContactService;
    CustomerContactRepository mvCustomerContactRepository;

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
        Page<Customer> customers = mvCustomerRepository.findAll(name, sex, birthday, phone, email, address, pageable);
        List<CustomerDTO> customerDTOs = CustomerDTO.fromCustomers(customers.getContent());
        this.setContactDefault(customerDTOs);
        return new PageImpl<>(customerDTOs, pageable, customerDTOs.size());
    }

    @Override
    public List<CustomerDTO> findCustomerNewInMonth() {
        List<CustomerDTO> customerDTOs = CustomerDTO.fromCustomers(mvCustomerRepository.findCustomerNewInMonth());
        this.setContactDefault(customerDTOs);
        return customerDTOs;
    }

    @Override
    public Optional<CustomerDTO> findById(Long id) {
        Optional<Customer> customer = mvCustomerRepository.findById(id);
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
            throw new ResourceNotFoundException("Customer not found!");
        }
        Customer customer = Customer.fromCustomerDTO(dto);
        customer.setCreatedBy(dto.getCreatedBy() != null ? dto.getCreatedBy() : CommonUtils.getUserPrincipal().getId());
        customer.setBonusPoints(0);
        Customer customerInserted = mvCustomerRepository.save(customer);
        CustomerContact customerContact = CustomerContact.builder()
                .customer(new Customer(customerInserted.getId()))
                .value(dto.getPhoneDefault())
                .isDefault("Y")
                .status(true).build();
        if (dto.getPhoneDefault() != null) {
            customerContact.setCode("P");
            mvCustomerContactService.save(customerContact);
        }
        if (dto.getEmailDefault() != null) {
            customerContact.setCode("E");
            mvCustomerContactService.save(customerContact);
        }
        if (dto.getAddressDefault() != null) {
            customerContact.setCode("A");
            mvCustomerContactService.save(customerContact);
        }
        systemLogService.writeLogCreate(MODULE.PRODUCT, ACTION.PRO_CUS_C, MasterObject.Customer, "Thêm mới khách hàng", customer.toString());
        logger.info("Create customer: {}", customer.toString());
        return CustomerDTO.fromCustomer(customerInserted);
    }

    @Transactional
    @Override
    public CustomerDTO update(CustomerDTO customer, Long customerId) {
        if (customer == null || customerId <= 0 || this.findById(customerId).isEmpty()) {
            throw new ResourceNotFoundException("Customer not found!");
        }
        Customer customerToUpdate = Customer.fromCustomerDTO(customer);
        customerToUpdate.setId(customerId);
        Customer customerUpdated = mvCustomerRepository.save(customerToUpdate);

        CustomerContact phoneDefault = null;
        CustomerContact emailDefault = null;
        CustomerContact addressDefault = null;
        if (customer.getPhoneDefault() != null || customer.getEmailDefault() != null || customer.getAddressDefault() != null) {
            List<CustomerContact> contacts = mvCustomerContactService.findContacts(customerId);
            for (CustomerContact contact : contacts) {
                String code = contact.getCode();
                boolean isStatus = contact.isStatus();
                boolean isDefault = "Y".equals(contact.getIsDefault());

                if ("P".equals(code) && isDefault && isStatus) {
                    phoneDefault = contact;
                }
                if ("E".equals(code) && isDefault && isStatus) {
                    emailDefault = contact;
                }
                if ("A".equals(code) && isDefault && isStatus) {
                    addressDefault = contact;
                }
            }

            if (customer.getPhoneDefault() != null && phoneDefault != null) {
                phoneDefault.setValue(customer.getPhoneDefault());
                //customerContactService.update(phoneDefault, customerId);
                mvCustomerContactRepository.save(phoneDefault);
            } else if (customer.getPhoneDefault() != null && !customer.getPhoneDefault().isEmpty()) {
                phoneDefault = CustomerContact.builder()
                    .customer(new Customer(customerId))
                    .code("P")
                    .value(customer.getPhoneDefault())
                    .isDefault("Y")
                    .status(true).build();
                mvCustomerContactService.save(phoneDefault);
            }

            if (customer.getEmailDefault() != null && emailDefault != null) {
                emailDefault.setValue(customer.getEmailDefault());
                //customerContactService.update(emailDefault, customerId);
                mvCustomerContactRepository.save(emailDefault);
            } else if (customer.getEmailDefault() != null && !customer.getEmailDefault().isEmpty()) {
                emailDefault = CustomerContact.builder()
                    .customer(new Customer(customerId))
                    .code("E")
                    .value(customer.getEmailDefault())
                    .isDefault("Y")
                    .status(true).build();
                mvCustomerContactService.save(emailDefault);
            }

            if (customer.getAddressDefault() != null && addressDefault != null) {
                addressDefault.setValue(customer.getAddressDefault());
                //customerContactService.update(addressDefault, customerId);
                mvCustomerContactRepository.save(addressDefault);
            } else if (customer.getAddressDefault() != null && !customer.getAddressDefault().isEmpty()) {
                addressDefault = CustomerContact.builder()
                    .customer(new Customer(customerId))
                    .code("A")
                    .value(customer.getAddressDefault())
                    .isDefault("Y")
                    .status(true).build();
                mvCustomerContactService.save(addressDefault);
            }
        }
        systemLogService.writeLogUpdate(MODULE.PRODUCT, ACTION.PRO_CUS_U, MasterObject.Customer, "Cập nhật thông tin khách hàng", customer.toString());
        logger.info("Update customer info {}", customer.toString());
        return CustomerDTO.fromCustomer(customerUpdated);
    }

    @Override
    public String delete(Long id) {
        Optional<CustomerDTO> customer = this.findById(id);
        if (customer.isEmpty()) {
            throw new BadRequestException("Customer not found!");
        }
        if (!mvOrderService.findAll().isEmpty()) {
            throw new DataInUseException(ErrorCode.ERROR_DATA_LOCKED.getDescription());
        }
        mvCustomerRepository.deleteById(id);
        systemLogService.writeLogDelete(MODULE.PRODUCT, ACTION.PRO_CUS_D, MasterObject.Customer, "Xóa khách hàng", customer.get().getCustomerName());
        logger.info("Deleted customer id={}", id);
        return MessageCode.DELETE_SUCCESS.getDescription();
    }

    private void setContactDefault(List<CustomerDTO> customerDTOs) {
        for (CustomerDTO c : customerDTOs) {
            CustomerContact phoneDefault = mvCustomerContactService.findContactPhoneUseDefault(c.getId());
            CustomerContact emailDefault = mvCustomerContactService.findContactEmailUseDefault(c.getId());
            CustomerContact addressDefault = mvCustomerContactService.findContactAddressUseDefault(c.getId());
            c.setPhoneDefault(phoneDefault != null ? phoneDefault.getValue() : "");
            c.setEmailDefault(emailDefault != null ? emailDefault.getValue() : "");
            c.setAddressDefault(addressDefault != null ? addressDefault.getValue() : "");
        }
    }

    @Override
    public List<PurchaseHistory> findPurchaseHistory(Long customerId, Integer year, Integer month) {
        List<PurchaseHistory> purchaseHistories = new ArrayList<>();
        if (year == null) {
            year = LocalDateTime.now().getYear();
        }
        List<Object[]> purchaseHistoriesRawValue = mvOrderRepository.findPurchaseHistory(customerId, year, month);
        //col 0 -> year
        //col 1 -> month
        //col 2 -> purchase qty
        //col 3 -> average value
        for (Object[] data : purchaseHistoriesRawValue) {
            PurchaseHistory ph = PurchaseHistory.builder()
                .customerId(customerId)
                .year(year)
                .month(Integer.parseInt(String.valueOf(data[1])))
                .purchaseQty(Integer.parseInt(String.valueOf(data[2])))
                .orderAvgValue(new BigDecimal(String.valueOf(data[3])))
                .build();
            purchaseHistories.add(ph);
        }
        return purchaseHistories;
    }
}