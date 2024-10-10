package com.flowiee.pms.repository.product;

import com.flowiee.pms.entity.product.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Integer> {
    @Query("from ProductPrice pp " +
           "where 1=1 " +
           "    and (:productBaseId is null or pp.productBase.id = :productBaseId) " +
           "    and (:productVariantId is null or pp.productVariant.id = :productVariantId) " +
           "    and pp.state = 'A'")
    ProductPrice findPricePresent(@Param("productBaseId") Integer productBaseId, @Param("productVariantId") Integer productVariantId);

    @Query("from ProductPrice pp " +
           "where 1=1 " +
           "    and (:productBaseId is null or pp.productBase.id = :productBaseId) " +
           "    and (:productVariantId is null or pp.productVariant.id = :productVariantId) ")
    List<ProductPrice> findPrices(@Param("productBaseId") Integer productBaseId, @Param("productVariantId") Integer productVariantId);
}