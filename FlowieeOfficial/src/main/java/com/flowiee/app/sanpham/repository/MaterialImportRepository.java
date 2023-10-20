package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.MaterialImport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialImportRepository extends JpaRepository<MaterialImport, Integer> {
}