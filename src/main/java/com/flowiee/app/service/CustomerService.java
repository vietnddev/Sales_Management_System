package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.flowiee.app.dto.CustomerDTO;
import com.flowiee.app.entity.Customer;

import java.util.Date;
import java.util.List;

public interface CustomerService extends BaseService<Customer> {
    List<Customer> findAll();

    List<CustomerDTO> findAll(String name, String sex, Date birthday, String phone, String email, String address);
}