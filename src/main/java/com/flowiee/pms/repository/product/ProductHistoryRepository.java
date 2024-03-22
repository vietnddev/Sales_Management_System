package com.flowiee.pms.repository.product;

import com.flowiee.pms.entity.product.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Integer> {
    @Query("from ProductHistory p where p.product.id=:productId")
    List<ProductHistory> findByProductId(@Param("productId") Integer productId);

    @Query("from ProductHistory p where p.productDetail.id=:productVariantId and p.field=:field order by p.id desc")
    List<ProductHistory> findHistoryChangeOfProductDetail(@Param("productVariantId") Integer productVariantId, @Param("field") String field);
}