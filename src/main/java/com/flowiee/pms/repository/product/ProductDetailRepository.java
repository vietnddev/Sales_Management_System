package com.flowiee.pms.repository.product;

import com.flowiee.pms.entity.product.ProductDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository <ProductDetail, Long>{
    @Query("from ProductDetail v " +
           "left join Product p on p.id = v.product.id " +
           "left join GarmentFactory g on g.id = v.garmentFactory.id " +
           "left join Supplier sp on sp.id = v.supplier.id " +
           "left join Category c on c.id = v.color.id and c.type = 'COLOR' " +
           "left join Category s on s.id = v.size.id and s.type = 'SIZE' " +
           "left join Category f on f.id = v.fabricType.id and f.type = 'FABRIC_TYPE' " +
           "where 1=1 " +
           "and (:productId is null or v.product.id=:productId) " +
           "and (:colorId is null or c.id=:colorId) " +
           "and (:sizeId is null or s.id=:sizeId) " +
           "and (:fabricTypeId is null or f.id=:fabricTypeId) " +
           "and (:availableForSales is null or :availableForSales = false or v.storageQty > 0) " +
           "order by v.variantName, s.name, c.name")
    List<ProductDetail> findAll(@Param("productId") Long productId,
                           @Param("colorId") Long colorId,
                           @Param("sizeId") Long sizeId,
                           @Param("fabricTypeId") Long fabricTypeId,
                           @Param("availableForSales") Boolean availableForSales,
                           Pageable pageable);
    
    @Query("from ProductDetail b where b.product.id=:productId and b.color.id=:colorId and b.size.id=:sizeId and b.fabricType.id=:fabricTypeId")
    ProductDetail findByColorAndSize(@Param("productId") Long productId, @Param("colorId") Long colorId, @Param("sizeId")  Long sizeId, @Param("fabricTypeId")  Long fabricTypeId);

    @Query("select sum(nvl(p.storageQty, 0)) from ProductDetail p where p.product.id=:productId and p.color.id=:colorId and p.size.id=:sizeId")
    Integer findQuantityBySizeOfEachColor(@Param("productId") Long productId, @Param("colorId") Long colorId, @Param("sizeId") Long sizeId);

    @Query("select sum(nvl(p.soldQty, 0)) as totalQtySell from ProductDetail p where p.product.id=:productId")
    Integer findTotalQtySell(@Param("productId") Long productId);

    @Modifying
    @Query("update ProductDetail p set p.storageQty = (p.storageQty + :soldQty) where p.id=:productVariantId")
    void updateQuantityIncrease(@Param("soldQty") Integer soldQty, @Param("productVariantId") Long productVariantId);

    @Modifying
    @Query("update ProductDetail p set p.storageQty = (p.storageQty - :soldQty), p.soldQty = (p.soldQty + :soldQty) where p.id=:productVariantId")
    void updateQuantityDecrease(@Param("soldQty") Integer soldQty, @Param("productVariantId") Long productVariantId);

    @Query("select sum(p.storageQty) from ProductDetail p where p.status = 'A'")
    Integer countTotalQuantity();
}