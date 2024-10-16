package com.flowiee.pms.repository.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.pms.entity.sales.GarmentFactory;

@Repository
public interface GarmentFactoryRepository extends JpaRepository<GarmentFactory, Long> {
}