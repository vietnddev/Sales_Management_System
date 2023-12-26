package com.flowiee.app.service.impl;

import com.flowiee.app.dto.CustomerDTO;
import com.flowiee.app.exception.DataInUseException;
import com.flowiee.app.model.role.SystemAction.ProductAction;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.entity.Customer;
import com.flowiee.app.repository.CustomerRepository;
import com.flowiee.app.service.CustomerContactService;
import com.flowiee.app.service.CustomerService;
import com.flowiee.app.service.OrderService;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.MessagesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            dto.setPhoneDefault(customerContactService.findPhoneUseDefault(customer.getId()));
            dto.setEmailDefault(customerContactService.findEmailUseDefault(customer.getId()));
            dto.setAddressDefault(customerContactService.findAddressUseDefault(customer.getId()));
            dataReturn.add(dto);
        });
        return dataReturn;
    }

    @Override
    public Customer findById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public String save(Customer customer) {
        if (customer == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        customer.setCreatedBy(CommonUtil.getCurrentAccountId());
        customerRepository.save(customer);
        systemLogService.writeLog(module, ProductAction.PRO_CUSTOMER_CREATE.name(), "Thêm mới khách hàng: " + customer.toString());
        logger.info(ProductServiceImpl.class.getName() + ": Thêm mới khách hàng " + customer.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String update(Customer customer, Integer id) {
        if (customer == null || id <= 0 || this.findById(id) == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        customer.setId(id);
        customerRepository.save(customer);
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
            throw new DataInUseException(MessagesUtil.ERROR_LOCKED);
        }
        customerRepository.deleteById(id);
        systemLogService.writeLog(module, ProductAction.PRO_CUSTOMER_DELETE.name(), "Xóa khách hàng: " + customer.toString());
        logger.info(ProductServiceImpl.class.getName() + ": Xóa khách hàng " + customer.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}