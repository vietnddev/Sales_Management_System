package com.flowiee.app.danhmuc.repository;

import com.flowiee.app.danhmuc.entity.TrangThaiGiaoHang;
import com.flowiee.app.sanpham.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrangThaiGiaoHangRepository extends JpaRepository<TrangThaiGiaoHang, Integer> {
    @Query("from TrangThaiGiaoHang t where t.donHang=:donHangId")
    List<TrangThaiGiaoHang> findByDonHangId(DonHang donHangId);
}