package com.flowiee.app.repositories;

import com.flowiee.app.model.sales.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
