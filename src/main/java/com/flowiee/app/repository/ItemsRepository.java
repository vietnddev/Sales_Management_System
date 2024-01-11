package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.Items;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Integer> {
    @Query("from Items i where i.orderCart.id=:idCart")
    List<Items> findByCartId(@Param("idCart") Integer idCart);

    @Query("select i.soLuong from Items i where i.orderCart.id=:cartId and i.productVariant.id=:productVariantId")
    Integer findSoLuongByBienTheSanPhamId(@Param("cartId") Integer cartId, @Param("productVariantId") Integer productVariantId);

    @Query("from Items i where i.orderCart.id=:cartId and i.productVariant.id=:productVariantId")
    Items findByCartAndProductVariant(@Param("cartId") Integer cartId, @Param("productVariantId") Integer productVariantId);

    @Query("select nvl(sum(p.giaBan * i.soLuong), 0) from Items i left join Price p on p.productVariant.id = i.productVariant.id where p.status = 'ACTIVE' and i.orderCart.id=:cartId")
    Double calTotalAmountWithoutDiscount(@Param("cartId") int cartId);

    @Modifying
    @Query("update Items i set i.soLuong=:quantity where i.id=:itemId")
    void updateItemQty(@Param("itemId") Integer itemId, @Param("quantity") Integer quantity);

    @Modifying
    @Query("delete Items")
    void deleteAllItems();
}