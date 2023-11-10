package com.flowiee.app.product.repository;

import com.flowiee.app.product.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Integer> {
    @Query("from Items i where i.orderCart.id=:idCart")
    List<Items> findByCartId(int idCart);

    @Query("select i.soLuong from Items i where i.productVariant.id=:productVariantId")
    Integer findSoLuongByBienTheSanPhamId(int productVariantId);
}