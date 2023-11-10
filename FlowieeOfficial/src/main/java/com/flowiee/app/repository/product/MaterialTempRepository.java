package com.flowiee.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.storage.MaterialTemp;

import java.util.List;

@Repository
public interface MaterialTempRepository extends JpaRepository<MaterialTemp, Integer> {
    @Query("from MaterialTemp m where m.code=:code")
    List<MaterialTemp> findByCode(String code);

    @Query("from MaterialTemp m where m.goodsImport.id=:importId")
    List<MaterialTemp> findByImportId(Integer importId);
}