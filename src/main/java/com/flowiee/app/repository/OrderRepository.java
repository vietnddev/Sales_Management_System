package com.flowiee.app.repository;

import com.flowiee.app.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("from Order d where d.trangThaiDonHang.id=:trangThaiDonHangId")
    List<Order> findByTrangThaiDonHang(Integer trangThaiDonHangId);

    @Query("from Order d order by d.id desc")
    List<Order> findDonHangMoiNhat();

    @Query("from Order d where d.customer.id=:khachHangId")
    List<Order> findByKhachHangId(int khachHangId);

    @Query("from Order d where d.nhanVienBanHang.id=:nhanVienId")
    List<Order> findByNhanvienId(int nhanVienId);

    @Query("from Order o where o.kenhBanHang.id=:salesChannelId")
    List<Order> findBySalesChannel(Integer salesChannelId);

    @Query("from Order o where o.trangThaiDonHang.id=:orderStatusId")
    List<Order> findByOrderStatus(Integer orderStatusId);

    @Query("from Order o where o.customer.id=:customerId")
    List<Order> findByCustomer(Integer customerId);
}