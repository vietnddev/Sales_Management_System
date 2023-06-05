package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BienTheSanPhamRepository extends JpaRepository <BienTheSanPham, Integer>{
    @Query("from BienTheSanPham b where b.sanPham.id=:sanPhamId order by b.loaiMauSac.tenLoai")
    List<BienTheSanPham> findListBienTheOfsanPham(int sanPhamId);

    @Query("from BienTheSanPham b where b.sanPham.id=:sanPhamId and b.loaiMauSac.id=:mauSacId and b.loaiKichCo.id=:kichCoId")
    BienTheSanPham findByMauSacAndKichCo(int sanPhamId, int mauSacId, int kichCoId);
}