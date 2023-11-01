package com.flowiee.app.category.repository;

import com.flowiee.app.category.entity.FabricType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FabricTypeRepository extends JpaRepository<FabricType, Integer> {
}