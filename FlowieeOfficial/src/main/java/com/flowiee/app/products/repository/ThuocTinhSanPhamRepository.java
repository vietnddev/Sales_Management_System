package com.flowiee.app.products.repository;

import com.flowiee.app.products.entity.BienTheSanPham;
import com.flowiee.app.products.entity.ThuocTinhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThuocTinhSanPhamRepository extends JpaRepository<ThuocTinhSanPham, Integer> {
    @Query(value = "from ThuocTinhSanPham tt where tt.bienTheSanPham=:productVariantID order by tt.thuTuHienThi asc")
    List<ThuocTinhSanPham> findByBienTheSanPham(BienTheSanPham productVariantID);
}
