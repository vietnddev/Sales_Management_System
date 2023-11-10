package com.flowiee.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.product.ProductVariantTemp;

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