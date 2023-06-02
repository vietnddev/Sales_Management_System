package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.GiaSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GiaSanPhamRepository extends JpaRepository<GiaSanPham, Integer> {
    @Query("from GiaSanPham g where g.bienTheSanPham=:bienTheSanPhamId")
    List<GiaSanPham> findListGiaBanOfSP(BienTheSanPham bienTheSanPhamId);

    @Query("select g.giaBan from GiaSanPham g where g.bienTheSanPham=:bienTheSanPhamId and g.trangThai=:trangThai")
    Double findGiaBanHienTai(BienTheSanPham bienTheSanPhamId, boolean trangThai);
}