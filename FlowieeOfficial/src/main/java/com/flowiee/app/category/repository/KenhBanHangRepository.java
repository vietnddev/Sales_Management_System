package com.flowiee.app.category.repository;

import com.flowiee.app.category.entity.KenhBanHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KenhBanHangRepository extends JpaRepository<KenhBanHang, Integer> {
}
