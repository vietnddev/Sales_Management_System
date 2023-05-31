package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.DonHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietDonHangRepository extends JpaRepository<DonHangChiTiet, Integer> {
    @Query("from DonHangChiTiet d where d.donHang=:idDonHang")
    List<DonHangChiTiet> findByordersID(int idDonHang);
}