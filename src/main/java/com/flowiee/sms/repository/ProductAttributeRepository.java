package com.flowiee.sms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.sms.entity.ProductAttribute;
import com.flowiee.sms.entity.ProductDetail;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {
    @Query(value = "from ProductAttribute t where t.productDetail=:productVariantId order by t.sort asc")
    List<ProductAttribute> findByProductVariantId(@Param("productVariantId") ProductDetail productDetailId);
}
