package com.flowiee.sms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.sms.entity.CustomerContact;

import java.util.List;

@Repository
public interface CustomerContactRepository extends JpaRepository<CustomerContact, Integer> {
    @Query("from CustomerContact c where c.customer.id=:customerId order by c.code, c.isDefault, c.status")
    List<CustomerContact> findByCustomerId(@Param("customerId") Integer customerId);

    @Query("from CustomerContact c where c.customer.id=:customerId and c.code='P' and c.isDefault='Y' and c.status=true")
    CustomerContact findPhoneUseDefault(@Param("customerId") Integer customerId);

    @Query("from CustomerContact c where c.customer.id=:customerId and c.code='E' and c.isDefault='Y' and c.status=true")
    CustomerContact findEmailUseDefault(@Param("customerId") Integer customerId);

    @Query("from CustomerContact c where c.customer.id=:customerId and c.code='A' and c.isDefault='Y'  and c.status=true")
    CustomerContact findAddressUseDefault(@Param("customerId") Integer customerId);
}