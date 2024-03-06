package com.flowiee.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.sms.entity.GarmentFactory;

@Repository
public interface GarmentFactoryRepository extends JpaRepository<GarmentFactory, Integer> {
}