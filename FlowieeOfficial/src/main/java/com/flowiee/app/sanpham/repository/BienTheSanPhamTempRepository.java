package com.flowiee.app.sanpham.repository;

import com.flowiee.app.sanpham.entity.ProductVariantTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BienTheSanPhamTempRepository extends JpaRepository <ProductVariantTemp, Integer>{
    @Query("from ProductVariantTemp b where b.sanPham.id=:sanPhamId order by b.loaiMauSac.tenLoai")
    List<ProductVariantTemp> findListBienTheOfsanPham(int sanPhamId);

    @Query("from ProductVariantTemp b where b.sanPham.id=:sanPhamId and b.loaiMauSac.id=:mauSacId and b.loaiKichCo.id=:kichCoId")
    ProductVariantTemp findByMauSacAndKichCo(int sanPhamId, int mauSacId, int kichCoId);

    @Query("from ProductVariantTemp b where b.goodsImport.id=:importId")
    List<ProductVariantTemp> findByImportId(Integer importId);
}