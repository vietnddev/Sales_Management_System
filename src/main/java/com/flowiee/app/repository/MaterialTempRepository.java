package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.MaterialTemp;

import java.util.List;

@Repository
public interface MaterialTempRepository extends JpaRepository<MaterialTemp, Integer> {
    @Query("from MaterialTemp m where m.ticketImport.id=:importId")
    List<MaterialTemp> findByImportId(@Param("importId") Integer importId);
    
    @Query("from MaterialTemp m where m.ticketImport.id=:importId and m.materialId=:materialId")
    MaterialTemp findMaterialInGoodsImport(@Param("importId") Integer importId, @Param("materialId") Integer materialId);
}