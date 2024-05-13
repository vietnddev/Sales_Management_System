package com.flowiee.pms.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.pms.entity.product.ProductVariantTemp;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductDetailTempRepository extends JpaRepository <ProductVariantTemp, Integer>{
    @Query("from ProductVariantTemp b where b.ticketImport.id=:importId")
    List<ProductVariantTemp> findByImportId(@Param("importId") Integer importId);
    
    @Query("from ProductVariantTemp p where p.ticketImport.id=:importId and p.productVariant.id=:productVariantId")
    ProductVariantTemp findProductVariantInGoodsImport(@Param("importId") Integer importId, @Param("productVariantId") Integer productVariantId);

    @Transactional
    @Modifying
    @Query("update ProductVariantTemp p set p.quantity = (p.quantity + :quantity) where p.id =:productVariantTempId")
    void updateQuantityIncrease(@Param("productVariantTempId") Integer productVariantTempId, @Param("quantity") Integer quantity);
}