package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.SanPhamBienTheImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariantImportRepository extends JpaRepository<SanPhamBienTheImport, Integer> {
}