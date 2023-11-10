package com.flowiee.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flowiee.app.entity.product.Price;
import com.flowiee.app.entity.product.ProductVariant;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Integer> {
    @Query("from Price g where g.productVariant=:productVariantId")
    List<Price> findListGiaBanOfSP(ProductVariant productVariantId);

    @Query("select g.giaBan from Price g where g.productVariant=:productVariantId and g.trangThai=:trangThai")
    Double findGiaBanHienTai(ProductVariant productVariantId, boolean trangThai);

    @Query("from Price g where g.productVariant=:productVariantId and g.trangThai=:trangThai")
    Price findGiaBanHienTaiModel(ProductVariant productVariantId, boolean trangThai);
}