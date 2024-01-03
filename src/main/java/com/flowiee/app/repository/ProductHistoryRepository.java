package com.flowiee.app.repository;

import com.flowiee.app.entity.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Integer> {
    @Query("from ProductHistory p where p.product.id=:productId")
    List<ProductHistory> findByProductId(@Param("productId") Integer productId);

    @Query("from ProductHistory p where p.productVariant.id=:productVariantId")
    List<ProductHistory> findByProductVariantId(@Param("productVariantId") Integer productVariantId);

    @Query("from ProductHistory p where p.productAttribute.id=:productAttributeId")
    List<ProductHistory> findByProductAttributeId(@Param("productAttributeId") Integer productAttributeId);

    @Query("from ProductHistory p where p.productPrice.id=:productPriceId")
    List<ProductHistory> findByProductPriceId(@Param("productPriceId") Integer productPriceId);
}