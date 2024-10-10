package com.flowiee.pms.repository.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.pms.entity.sales.Items;

import java.util.List;

@Repository
public interface CartItemsRepository extends JpaRepository<Items, Integer> {
    @Query("from Items i where i.orderCart.id=:idCart")
    List<Items> findByCartId(@Param("idCart") Integer idCart);

    @Query("select i.quantity from Items i where i.orderCart.id=:cartId and i.productDetail.id=:productVariantId")
    Integer findQuantityByProductVariantId(@Param("cartId") Integer cartId, @Param("productVariantId") Integer productVariantId);

    @Query("from Items i where i.orderCart.id=:cartId and i.productDetail.id=:productVariantId")
    Items findByCartAndProductVariant(@Param("cartId") Integer cartId, @Param("productVariantId") Integer productVariantId);

    @Query("select nvl(sum(nvl((case when i.price is not null then i.price else i.priceOriginal end), 0) * i.quantity), 0) " +
           "from Items i " +
           "where i.orderCart.id=:cartId")
    Double calTotalAmountWithoutDiscount(@Param("cartId") int cartId);

    @Modifying
    @Query("update Items i set i.quantity=:quantity where i.id=:itemId")
    void updateItemQty(@Param("itemId") Integer itemId, @Param("quantity") Integer quantity);

    @Modifying
    @Query("delete Items")
    void deleteAllItems();

    @Modifying
    @Query("delete Items where orderCart.id=:cartId")
    void deleteAllItems(@Param("cartId") Integer cartId);
}