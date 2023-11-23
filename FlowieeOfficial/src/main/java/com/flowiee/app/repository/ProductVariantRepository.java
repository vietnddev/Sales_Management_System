package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.ProductVariant;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository <ProductVariant, Integer>{
    @Query("from ProductVariant b where b.product.id=:sanPhamId and b.color.id=:mauSacId and b.size.id=:kichCoId")
    ProductVariant findByMauSacAndKichCo(int sanPhamId, int mauSacId, int kichCoId);

    @Query("from ProductVariant b where b.ticketImportGoods.id=:importId")
    List<ProductVariant> findByImportId(Integer importId);

    @Query("from ProductVariant p where p.fabricType.id=:fabricTypeId")
    List<ProductVariant> findByFabricType(Integer fabricTypeId);

    @Query("from ProductVariant p where p.color.id=:colorId")
    List<ProductVariant> findByColor(Integer colorId);

    @Query("from ProductVariant p where p.size.id=:sizeId")
    List<ProductVariant> findBySize(Integer sizeId);
}