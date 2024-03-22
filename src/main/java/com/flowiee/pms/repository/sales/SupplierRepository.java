package com.flowiee.pms.repository.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.pms.entity.sales.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}