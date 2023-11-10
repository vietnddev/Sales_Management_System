package com.flowiee.app.product.repository;


import com.flowiee.app.product.entity.CustomerContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerContactRepository extends JpaRepository<CustomerContact, Integer> {
    @Query("from CustomerContact c where c.customer.id=:customerId")
    List<CustomerContact> findByCustomerId(Integer customerId);
}