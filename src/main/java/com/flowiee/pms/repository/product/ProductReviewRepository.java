package com.flowiee.pms.repository.product;

import com.flowiee.pms.entity.product.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
    Page<ProductReview> findByProduct(Integer productId, Pageable pageable);
}