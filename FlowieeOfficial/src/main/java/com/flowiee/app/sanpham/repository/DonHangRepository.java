package com.flowiee.app.sanpham.repository;

import com.flowiee.app.danhmuc.entity.TrangThaiDonHang;
import com.flowiee.app.sanpham.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    @Query("from DonHang d where d.trangThaiDonHang=:trangThaiDonHang")
    List<DonHang> findByTrangThaiDonHang(TrangThaiDonHang trangThaiDonHang);

    @Query("from DonHang d order by d.id desc")
    List<DonHang> findDonHangMoiNhat();

    @Query("from DonHang d " +
           "where (:searchTxt is null or (d.khachHang.soDienThoai like %:searchTxt% or d.maDonHang like %:searchTxt%)) " +
           "and (:kenhBanHangId = 0 or d.kenhBanHang.id=:kenhBanHangId) " +
           "and (:trangThaiDonDangId = 0 or d.trangThaiDonHang.id=:trangThaiDonDangId)")
    List<DonHang> findAll(@Param("searchTxt") String searchTxt,
                          @Param("kenhBanHangId") int kenhBanHangId,
                          @Param("trangThaiDonDangId") int trangThaiDonDangId);
}