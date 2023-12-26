package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.Customer;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("select distinct c from Customer c " +
            " left join CustomerContact cc on c.id = cc.customer.id "
            + "where (:name is null or c.tenKhachHang like %:name%) "
            + "and (:sex is null or c.gioiTinh=:sex) "
            + "and (:birthday is null or c.birthday=:birthday) "
            + "and (:phone is null or (cc.code = 'P' and cc.isDefault = 'Y' and cc.status = true and cc.value=:phone)) "
            + "and (:email is null or (cc.code = 'E' and cc.isDefault = 'Y' and cc.status = true and cc.value=:email)) "
            + "and (:address is null or (cc.code = 'A' and cc.isDefault = 'Y' and cc.status = true and cc.value=:address)) ")
    List<Customer> findAll(String name, String sex, Date birthday, String phone, String email, String address);
}