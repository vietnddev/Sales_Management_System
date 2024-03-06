package com.flowiee.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.sms.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}