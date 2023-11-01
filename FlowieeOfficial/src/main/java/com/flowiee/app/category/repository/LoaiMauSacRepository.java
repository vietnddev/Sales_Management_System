package com.flowiee.app.category.repository;

import com.flowiee.app.category.entity.LoaiMauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiMauSacRepository extends JpaRepository<LoaiMauSac, Integer> {
}
