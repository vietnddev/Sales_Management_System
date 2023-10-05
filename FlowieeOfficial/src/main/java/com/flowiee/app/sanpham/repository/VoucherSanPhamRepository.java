package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.VoucherSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherSanPhamRepository extends JpaRepository<VoucherSanPham, Integer> {
    @Query("from VoucherSanPham where voucherId=:voucherId")
    List<VoucherSanPham> findByVoucherId(Integer voucherId);
}