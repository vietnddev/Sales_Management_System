package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.GiaSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GiaSanPhamRepository extends JpaRepository<GiaSanPham, Integer> {

    @Query("From GiaSanPham where productVariantID =:productVariantID Order By id Desc")
    List<GiaSanPham> findByproductVariantID(int productVariantID);

}