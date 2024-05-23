package com.flowiee.pms.repository.sales;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.pms.entity.sales.Customer;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("select distinct c from Customer c " +
           "left join CustomerContact cc on c.id = cc.customer.id " +
           "where (:name is null or c.customerName like %:name%) " +
           "and (:sex is null or c.sex=:sex) " +
           "and (:birthday is null or c.dateOfBirth=:birthday) " +
           "and (:phone is null or (cc.code = 'P' and cc.isDefault = 'Y' and cc.status = true and cc.value=:phone)) " +
           "and (:email is null or (cc.code = 'E' and cc.isDefault = 'Y' and cc.status = true and cc.value=:email)) " +
           "and (:address is null or (cc.code = 'A' and cc.isDefault = 'Y' and cc.status = true and cc.value=:address)) " +
           "order by c.customerName")
    Page<Customer> findAll(@Param("name") String name,
                           @Param("sex") String sex,
                           @Param("birthday") Date birthday,
                           @Param("phone") String phone,
                           @Param("email") String email,
                           @Param("address") String address,
                           Pageable pageable
    );

    @Query("from Customer c where extract(month from c.createdAt) = extract(month from current_date)")
    List<Customer> findCustomerNewInMonth();
}