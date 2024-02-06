package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.ProductVariantTemp;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductVariantTempRepository extends JpaRepository <ProductVariantTemp, Integer>{
    @Query("from ProductVariantTemp b where b.ticketImport.id=:importId")
    List<ProductVariantTemp> findByImportId(@Param("importId") Integer importId);
    
    @Query("from ProductVariantTemp p where p.ticketImport.id=:importId and p.productVariantId=:productVariantId")
    ProductVariantTemp findProductVariantInGoodsImport(@Param("importId") Integer importId, @Param("productVariantId") Integer productVariantId);

    @Transactional
    @Modifying
    @Query("update ProductVariantTemp p set p.quantity = (p.quantity + :quantity) where p.id =:productVariantTempId")
    void updateQuantityIncrease(@Param("productVariantTempId") Integer productVariantTempId, @Param("quantity") Integer quantity);
}