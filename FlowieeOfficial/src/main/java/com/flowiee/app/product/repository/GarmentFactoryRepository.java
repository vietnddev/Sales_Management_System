package com.flowiee.app.product.repository;

import com.flowiee.app.product.entity.GarmentFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarmentFactoryRepository extends JpaRepository<GarmentFactory, Integer> {
}