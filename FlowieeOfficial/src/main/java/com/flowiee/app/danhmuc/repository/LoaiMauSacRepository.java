package com.flowiee.app.danhmuc.repository;

import com.flowiee.app.danhmuc.entity.LoaiMauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiMauSacRepository extends JpaRepository<LoaiMauSac, Integer> {
}
