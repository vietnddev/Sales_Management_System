package com.flowiee.app.services;

import com.flowiee.app.model.Customer;
import com.flowiee.app.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
