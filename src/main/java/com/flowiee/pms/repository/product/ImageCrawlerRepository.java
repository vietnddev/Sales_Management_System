package com.flowiee.pms.repository.product;

import com.flowiee.pms.entity.product.ImageCrawled;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageCrawlerRepository extends JpaRepository<ImageCrawled, Long> {

}