package com.flowiee.app.danhmuc.repository;

import com.flowiee.app.danhmuc.entity.DonViTinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonViTinhRepository extends JpaRepository<DonViTinh, Integer> {
}