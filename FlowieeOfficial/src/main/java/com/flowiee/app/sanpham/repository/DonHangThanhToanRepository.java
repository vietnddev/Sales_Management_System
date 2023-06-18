package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.DonHangThanhToan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonHangThanhToanRepository extends JpaRepository<DonHangThanhToan, Integer> {
    @Query("from DonHangThanhToan d where d.donHang.id=:id")
    List<DonHangThanhToan> findByDonHangId(int id);
}