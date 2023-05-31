package com.flowiee.app.danhmuc.repository;

import com.flowiee.app.danhmuc.entity.LoaiKichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiKichCoRepository extends JpaRepository<LoaiKichCo, Integer> {
}
