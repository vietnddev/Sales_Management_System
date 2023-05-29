package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BienTheSanPhamRepository extends JpaRepository <BienTheSanPham, Integer>{
    // Lấy danh sách biến thể theo id sản phẩm
    @Query("from BienTheSanPham v where v.loaiBienThe=:loaiBienThe and v.sanPham=:sanPham")
    List<BienTheSanPham> findListBienTheOfsanPham(String loaiBienThe, SanPham sanPham);
}
