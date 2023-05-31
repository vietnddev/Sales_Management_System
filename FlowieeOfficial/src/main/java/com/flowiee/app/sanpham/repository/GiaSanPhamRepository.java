package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.GiaSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GiaSanPhamRepository extends JpaRepository<GiaSanPham, Integer> {

    @Query("From GiaSanPham p where p.bienTheSanPham=:bienTheSanPham Order By p.id desc")
    List<GiaSanPham> findByproductVariantID(BienTheSanPham bienTheSanPham);

}