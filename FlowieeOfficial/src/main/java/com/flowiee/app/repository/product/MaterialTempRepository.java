package com.flowiee.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.MaterialTemp;

import java.util.List;

@Repository
public interface MaterialTempRepository extends JpaRepository<MaterialTemp, Integer> {
    @Query("from MaterialTemp m where m.ticketImportGoods.id=:importId")
    List<MaterialTemp> findByImportId(Integer importId);
    
    @Query("from MaterialTemp m where m.ticketImportGoods.id=:importId and m.materialId=:materialId")
    MaterialTemp findMaterialInGoodsImport(Integer importId, Integer materialId);
}