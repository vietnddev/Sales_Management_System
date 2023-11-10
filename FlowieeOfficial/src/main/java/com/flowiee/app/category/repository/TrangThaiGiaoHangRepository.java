package com.flowiee.app.category.repository;

import com.flowiee.app.category.entity.TrangThaiGiaoHang;
import com.flowiee.app.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrangThaiGiaoHangRepository extends JpaRepository<TrangThaiGiaoHang, Integer> {
    @Query("from TrangThaiGiaoHang t where t.order=:orderId")
    List<TrangThaiGiaoHang> findByDonHangId(Order orderId);
}