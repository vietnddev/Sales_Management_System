package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.ProductVariant;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository <ProductVariant, Integer>{
    @Query("from ProductVariant b where b.product.id=:productId and b.color.id=:colorId and b.size.id=:sizeId")
    ProductVariant findByColorAndSize(@Param("productId") Integer productId, @Param("colorId") Integer colorId, @Param("sizeId")  Integer sizeId);

    @Query("from ProductVariant b where b.ticketImportGoods.id=:importId")
    List<ProductVariant> findByImportId(@Param("importId") Integer importId);

    @Query("from ProductVariant p where p.fabricType.id=:fabricTypeId")
    List<ProductVariant> findByFabricType(@Param("fabricTypeId") Integer fabricTypeId);

    @Query("from ProductVariant p where p.color.id=:colorId")
    List<ProductVariant> findByColor(@Param("colorId") Integer colorId);

    @Query("from ProductVariant p where p.size.id=:sizeId")
    List<ProductVariant> findBySize(@Param("sizeId") Integer sizeId);
}