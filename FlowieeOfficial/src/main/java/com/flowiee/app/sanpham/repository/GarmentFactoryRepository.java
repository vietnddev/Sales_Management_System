package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.GarmentFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarmentFactoryRepository extends JpaRepository<GarmentFactory, Integer> {
}