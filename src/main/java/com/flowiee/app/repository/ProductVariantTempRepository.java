package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.ProductVariantTemp;

import java.util.List;

@Repository
public interface ProductVariantTempRepository extends JpaRepository <ProductVariantTemp, Integer>{
    @Query("from ProductVariantTemp b where b.ticketImportGoods.id=:importId")
    List<ProductVariantTemp> findByImportId(@Param("importId") Integer importId);
    
    @Query("from ProductVariantTemp p where p.ticketImportGoods.id=:importId and p.productVariantId=:productVariantId")
    ProductVariantTemp findProductVariantInGoodsImport(@Param("importId") Integer importId, @Param("productVariantId") Integer productVariantId);
}