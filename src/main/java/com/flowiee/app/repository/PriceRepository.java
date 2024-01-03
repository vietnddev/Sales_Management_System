package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flowiee.app.entity.Price;
import com.flowiee.app.entity.ProductVariant;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Integer> {
    @Query("from Price p where p.productVariant.id=:productVariantId order by p.status desc, p.createdAt desc")
    List<Price> findPriceByProductVariant(@Param("productVariantId") Integer productVariantId);

    @Query("from Price g where g.productVariant=:productVariantId and g.status=:status")
    Price findGiaBanHienTai(@Param("productVariantId") ProductVariant productVariantId, @Param("status") String status);
}