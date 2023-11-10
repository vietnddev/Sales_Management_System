package com.flowiee.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.ProductVariantTemp;

import java.util.List;

@Repository
public interface ProductVariantTempRepository extends JpaRepository <ProductVariantTemp, Integer>{
    @Query("from ProductVariantTemp b where b.goodsImport.id=:importId")
    List<ProductVariantTemp> findByImportId(Integer importId);
    
    @Query("from ProductVariantTemp p where p.goodsImport.id=:importId and p.producVariantId=:producVariantId")
    ProductVariantTemp findProductVariantInGoodsImport(Integer importId, Integer productVariantId);
}