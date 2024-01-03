package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.MaterialHistory;

import java.util.List;

@Repository
public interface MaterialHistoryRepository extends JpaRepository<MaterialHistory, Integer> {
    @Query("from MaterialHistory m where m.material.id=:materialId")
    List<MaterialHistory> findByMaterialId(@Param("materialId") Integer materialId);

    @Query("from MaterialHistory m where m.fieldName=:fieldName")
    List<MaterialHistory> findByFieldName(@Param("fieldName") String fieldName);
}