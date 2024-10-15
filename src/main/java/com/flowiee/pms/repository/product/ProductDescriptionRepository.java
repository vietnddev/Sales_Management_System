package com.flowiee.pms.repository.product;

import com.flowiee.pms.entity.product.ProductDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Long> {
}