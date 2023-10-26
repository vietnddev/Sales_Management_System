package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Integer> {
    @Query("from Price g where g.bienTheSanPham=:bienTheSanPhamId")
    List<Price> findListGiaBanOfSP(BienTheSanPham bienTheSanPhamId);

    @Query("select g.giaBan from Price g where g.bienTheSanPham=:bienTheSanPhamId and g.trangThai=:trangThai")
    Double findGiaBanHienTai(BienTheSanPham bienTheSanPhamId, boolean trangThai);

    @Query("from Price g where g.bienTheSanPham=:bienTheSanPhamId and g.trangThai=:trangThai")
    Price findGiaBanHienTaiModel(BienTheSanPham bienTheSanPhamId, boolean trangThai);
}