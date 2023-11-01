package com.flowiee.app.product.repository;

import com.flowiee.app.product.entity.ProductVariantTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantTempRepository extends JpaRepository <ProductVariantTemp, Integer>{
    @Query("from ProductVariantTemp b where b.product.id=:sanPhamId order by b.loaiMauSac.tenLoai")
    List<ProductVariantTemp> findListBienTheOfsanPham(int sanPhamId);

    @Query("from ProductVariantTemp b where b.product.id=:sanPhamId and b.loaiMauSac.id=:mauSacId and b.loaiKichCo.id=:kichCoId")
    ProductVariantTemp findByMauSacAndKichCo(int sanPhamId, int mauSacId, int kichCoId);

    @Query("from ProductVariantTemp b where b.goodsImport.id=:importId")
    List<ProductVariantTemp> findByImportId(Integer importId);
}