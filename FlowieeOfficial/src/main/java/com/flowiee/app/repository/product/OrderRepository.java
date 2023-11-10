package com.flowiee.app.repository.product;

import com.flowiee.app.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("from Order d where d.trangThaiDonHang.id=:trangThaiDonHangId")
    List<Order> findByTrangThaiDonHang(Integer trangThaiDonHangId);

    @Query("from Order d order by d.id desc")
    List<Order> findDonHangMoiNhat();

    @Query("from Order d " +
           "where (:searchTxt is null or (d.maDonHang like %:searchTxt%)) " + //d.khachHang.soDienThoai like %:searchTxt% or
           "and (:kenhBanHangId = 0 or d.kenhBanHang.id=:kenhBanHangId) " +
           "and (:trangThaiDonDangId = 0 or d.trangThaiDonHang.id=:trangThaiDonDangId)")
    List<Order> findAll(@Param("searchTxt") String searchTxt,
                        @Param("kenhBanHangId") int kenhBanHangId,
                        @Param("trangThaiDonDangId") int trangThaiDonDangId);

    @Query("from Order d where d.customer.id=:khachHangId")
    List<Order> findByKhachHangId(int khachHangId);

    @Query("from Order d where d.nhanVienBanHang.id=:nhanVienId")
    List<Order> findByNhanvienId(int nhanVienId);
}