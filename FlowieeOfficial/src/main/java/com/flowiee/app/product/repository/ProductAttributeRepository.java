package com.flowiee.app.product.repository;

import com.flowiee.app.product.entity.ProductVariant;
import com.flowiee.app.product.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {
    @Query(value = "from ProductAttribute t where t.productVariant=:productVariantID order by t.sort asc")
    List<ProductAttribute> findByBienTheSanPham(ProductVariant productVariantID);
}
