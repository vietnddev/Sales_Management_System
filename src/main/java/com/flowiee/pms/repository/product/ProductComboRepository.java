package com.flowiee.pms.repository.product;

import com.flowiee.pms.entity.product.ProductCombo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductComboRepository extends JpaRepository<ProductCombo, Integer> {
}