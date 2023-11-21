package com.flowiee.app.repository.product;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.CustomerContact;

import java.util.List;

@Repository
public interface CustomerContactRepository extends JpaRepository<CustomerContact, Integer> {
    @Query("from CustomerContact c where c.customer.id=:customerId")
    List<CustomerContact> findByCustomerId(Integer customerId);

    @Query("from CustomerContact c where c.customer.id=:customerId and c.code='PHONE' and c.isDefault=true")
    CustomerContact findPhoneUseDefault(Integer customerId);

    @Query("from CustomerContact c where c.customer.id=:customerId and c.code='EMAIL' and c.isDefault=true")
    CustomerContact findEmailUseDefault(Integer customerId);

    @Query("from CustomerContact c where c.customer.id=:customerId and c.code='ADDRESS' and c.isDefault=true")
    CustomerContact findAddressUseDefault(Integer customerId);
}