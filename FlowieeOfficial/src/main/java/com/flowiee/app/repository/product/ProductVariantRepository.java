package com.flowiee.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.ProductVariant;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository <ProductVariant, Integer>{
    @Query("from ProductVariant b where b.product.id=:sanPhamId order by b.loaiMauSac.tenLoai")
    List<ProductVariant> findListBienTheOfsanPham(int sanPhamId);

    @Query("from ProductVariant b where b.product.id=:sanPhamId and b.loaiMauSac.id=:mauSacId and b.loaiKichCo.id=:kichCoId")
    ProductVariant findByMauSacAndKichCo(int sanPhamId, int mauSacId, int kichCoId);

    @Query("from ProductVariant b where b.goodsImport.id=:importId")
    List<ProductVariant> findByImportId(Integer importId);
}