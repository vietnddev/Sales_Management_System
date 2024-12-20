package com.flowiee.pms.repository.product;

import com.flowiee.pms.entity.product.ProductCrawled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCrawlerRepository extends JpaRepository<ProductCrawled, Long> {

}