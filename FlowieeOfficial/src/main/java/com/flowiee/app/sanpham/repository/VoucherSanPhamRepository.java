package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.VoucherSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherSanPhamRepository extends JpaRepository<VoucherSanPham, Integer> {
}