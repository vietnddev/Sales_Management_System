package com.flowiee.app.category.repository;

import com.flowiee.app.category.entity.LoaiSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiSanPhamRepository extends JpaRepository<LoaiSanPham, Integer> {
    @Query("From LoaiSanPham l where l.tenLoai=:tenLoai")
    LoaiSanPham findByTen(String tenLoai);
}
