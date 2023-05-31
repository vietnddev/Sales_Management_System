package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.ThuocTinhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThuocTinhSanPhamRepository extends JpaRepository<ThuocTinhSanPham, Integer> {
    @Query(value = "from ThuocTinhSanPham t where t.bienTheSanPham=:productVariantID order by t.sort asc")
    List<ThuocTinhSanPham> findByBienTheSanPham(BienTheSanPham productVariantID);
}
