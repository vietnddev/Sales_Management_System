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
public interface ProductDetailTempRepository extends JpaRepository <ProductVariantTemp, Long>{
    @Query("from ProductVariantTemp b where b.ticketImport.id=:importId")
    List<ProductVariantTemp> findByImportId(@Param("importId") Long importId);
    
    @Query("from ProductVariantTemp p where p.ticketImport.id=:importId and p.productVariant.id=:productVariantId")
    ProductVariantTemp findProductVariantInGoodsImport(@Param("importId") Long importId, @Param("productVariantId") Long productVariantId);

    @Transactional
    @Modifying
    @Query("update ProductVariantTemp p set p.quantity = (p.quantity + :quantity) where p.id =:productVariantTempId")
    void updateQuantityIncrease(@Param("productVariantTempId") Long productVariantTempId, @Param("quantity") int quantity);

    @Transactional
    @Modifying
    @Query("update ProductVariantTemp p set p.storageQty=:storageQty where p.id =:productVariantTempId")
    void updateStorageQuantity(@Param("productVariantTempId") Long productVariantTempId, @Param("storageQty") int storageQty);

    @Query("from ProductVariantTemp p where p.productVariant.id=:productVariantId order by p.createdAt desc")
    List<ProductVariantTemp> findByProductVariantId(@Param("productVariantId") Long productVariantId);
}