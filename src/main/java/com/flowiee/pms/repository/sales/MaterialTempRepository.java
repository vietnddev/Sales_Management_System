package com.flowiee.pms.repository.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.pms.entity.product.MaterialTemp;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MaterialTempRepository extends JpaRepository<MaterialTemp, Integer> {
    @Query("from MaterialTemp m where m.ticketImport.id=:importId")
    List<MaterialTemp> findByImportId(@Param("importId") Integer importId);
    
    @Query("from MaterialTemp m where m.ticketImport.id=:importId and m.material.id=:materialId")
    MaterialTemp findMaterialInGoodsImport(@Param("importId") Integer importId, @Param("materialId") Integer materialId);

    @Transactional
    @Modifying
    @Query("update MaterialTemp m set m.quantity = (m.quantity + :quantity) where m.id =:materialTempId")
    void updateQuantityIncrease(@Param("materialTempId") Integer materialTempId, @Param("quantity") Integer quantity);

    @Transactional
    @Modifying
    @Query("update MaterialTemp m set m.storageQty=:storageQty where m.id =:materialTempId")
    void updateStorageQuantity(@Param("materialTempId") Integer materialTempId, @Param("storageQty") Integer storageQty);
}