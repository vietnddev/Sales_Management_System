package com.flowiee.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.storage.Material;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    @Query("from Material m where m.code=:code")
    List<Material> findByCode(String code);

    @Query("from Material m where m.goodsImport.id=:importId")
    List<Material> findByImportId(Integer importId);
}