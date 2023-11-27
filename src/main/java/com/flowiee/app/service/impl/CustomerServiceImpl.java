package com.flowiee.app.service.impl;

import com.flowiee.app.model.role.KhachHangAction;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.entity.Customer;
import com.flowiee.app.repository.CustomerRepository;
import com.flowiee.app.service.CustomerService;
import com.flowiee.app.service.AccountService;
import com.flowiee.app.service.SystemLogService;

import com.flowiee.app.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private static final String module = SystemModule.SAN_PHAM.name();

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SystemLogService systemLogService;
    @Autowired
    private AccountService accountService;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
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
        customer.setCreatedBy(accountService.findCurrentAccountId());
        customerRepository.save(customer);
        systemLogService.writeLog(module, KhachHangAction.CREATE_KHACHHANG.name(), "Thêm mới khách hàng: " + customer.toString());
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
        systemLogService.writeLog(module, KhachHangAction.UPDATE_KHACHHANG.name(), "Cập nhật thông tin khách hàng: " + customer.toString());
        logger.info(ProductServiceImpl.class.getName() + ": Cập nhật khách hàng " + customer.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }

    @Override
    public String delete(Integer id) {
        Customer customer = this.findById(id);
        if (id <= 0 || customer == null) {
            return AppConstants.SERVICE_RESPONSE_FAIL;
        }
        customerRepository.deleteById(id);
        systemLogService.writeLog(module, KhachHangAction.DELETE_KHACHHANG.name(), "Xóa khách hàng: " + customer.toString());
        logger.info(ProductServiceImpl.class.getName() + ": Xóa khách hàng " + customer.toString());
        return AppConstants.SERVICE_RESPONSE_SUCCESS;
    }
}