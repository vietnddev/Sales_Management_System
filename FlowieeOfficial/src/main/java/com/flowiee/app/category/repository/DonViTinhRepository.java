package com.flowiee.app.category.repository;

import com.flowiee.app.category.entity.DonViTinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonViTinhRepository extends JpaRepository<DonViTinh, Integer> {
}