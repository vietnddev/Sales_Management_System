package com.flowiee.pms.repository.product;

import com.flowiee.pms.entity.product.GiftCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftCatalogRepository extends JpaRepository<GiftCatalog, Long> {
    List<GiftCatalog> findByIsActiveTrue();
}