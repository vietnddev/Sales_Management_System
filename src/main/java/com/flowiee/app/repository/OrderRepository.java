package com.flowiee.app.repository;

import com.flowiee.app.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("from Order d where d.trangThaiDonHang.id=:trangThaiDonHangId")
    List<Order> findByTrangThaiDonHang(@Param("trangThaiDonHangId") Integer trangThaiDonHangId);

    @Query("from Order d order by d.id desc")
    List<Order> findDonHangMoiNhat();

    @Query("from Order d where d.customer.id=:customerId")
    List<Order> findByKhachHangId(@Param("customerId") Integer customerId);

    @Query("from Order d where d.nhanVienBanHang.id=:nhanVienId")
    List<Order> findByNhanvienId(@Param("nhanVienId") Integer nhanVienId);

    @Query("from Order o where o.kenhBanHang.id=:salesChannelId")
    List<Order> findBySalesChannel(@Param("salesChannelId") Integer salesChannelId);

    @Query("from Order o where o.trangThaiDonHang.id=:orderStatusId")
    List<Order> findByOrderStatus(@Param("orderStatusId") Integer orderStatusId);

    @Query("from Order o where o.customer.id=:customerId")
    List<Order> findByCustomer(@Param("customerId") Integer customerId);
}