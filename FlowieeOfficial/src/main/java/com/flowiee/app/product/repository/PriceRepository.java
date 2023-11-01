package com.flowiee.app.product.repository;

import com.flowiee.app.product.entity.ProductVariant;
import com.flowiee.app.product.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Integer> {
    @Query("from Price g where g.productVariant=:productVariantId")
    List<Price> findListGiaBanOfSP(ProductVariant productVariantId);

    @Query("select g.giaBan from Price g where g.productVariant=:productVariantId and g.trangThai=:trangThai")
    Double findGiaBanHienTai(ProductVariant productVariantId, boolean trangThai);

    @Query("from Price g where g.productVariant=:productVariantId and g.trangThai=:trangThai")
    Price findGiaBanHienTaiModel(ProductVariant productVariantId, boolean trangThai);
}