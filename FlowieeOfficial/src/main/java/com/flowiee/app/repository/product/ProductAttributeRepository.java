package com.flowiee.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.product.ProductAttribute;
import com.flowiee.app.entity.product.ProductVariant;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Integer> {
    @Query(value = "from ProductAttribute t where t.productVariant=:productVariantID order by t.sort asc")
    List<ProductAttribute> findByBienTheSanPham(ProductVariant productVariantID);
}
