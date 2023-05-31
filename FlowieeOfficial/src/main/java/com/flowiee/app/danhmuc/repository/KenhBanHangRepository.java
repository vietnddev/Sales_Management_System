package com.flowiee.app.danhmuc.repository;

import com.flowiee.app.danhmuc.entity.KenhBanHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KenhBanHangRepository extends JpaRepository<KenhBanHang, Integer> {
}
