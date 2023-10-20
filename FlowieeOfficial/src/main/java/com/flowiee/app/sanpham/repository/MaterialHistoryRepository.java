package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.MaterialHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialHistoryRepository extends JpaRepository<MaterialHistory, Integer> {
}