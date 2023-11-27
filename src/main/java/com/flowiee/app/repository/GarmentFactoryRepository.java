package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.GarmentFactory;

@Repository
public interface GarmentFactoryRepository extends JpaRepository<GarmentFactory, Integer> {
}