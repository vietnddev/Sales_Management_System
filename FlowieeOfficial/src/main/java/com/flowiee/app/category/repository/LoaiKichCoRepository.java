package com.flowiee.app.category.repository;

import com.flowiee.app.category.entity.LoaiKichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiKichCoRepository extends JpaRepository<LoaiKichCo, Integer> {
}
