package com.flowiee.pms.repository.product;

import com.flowiee.pms.entity.product.ProductDamaged;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDamagedRepository extends JpaRepository<ProductDamaged, Long> {

}