package com.flowiee.pms.repository.product;

import com.flowiee.pms.entity.product.ProductDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository <ProductDetail, Integer>{
    @Query("from ProductDetail v " +
           "left join Product p on p.id = v.product.id " +
           "left join GarmentFactory g on g.id = v.garmentFactory.id " +
           "left join Supplier sp on sp.id = v.supplier.id " +
           "left join TicketImport ti on ti.id = v.ticketImport.id " +
           "left join Category c on c.id = v.color.id and c.type = 'COLOR' " +
           "left join Category s on s.id = v.size.id and s.type = 'SIZE' " +
           "left join Category f on f.id = v.fabricType.id and f.type = 'FABRIC_TYPE' " +
           "where 1=1 " +
           "and (:productId is null or v.product.id=:productId) " +
           "and (:colorId is null or c.id=:colorId) " +
           "and (:sizeId is null or s.id=:sizeId) " +
           "and (:fabricTypeId is null or f.id=:fabricTypeId) " +
           "and (:ticketImportId is null or ti.id=:ticketImportId) " +
           "order by v.variantName, s.name, c.name")
    List<ProductDetail> findAll(@Param("productId") Integer productId,
                           @Param("ticketImportId") Integer ticketImportId,
                           @Param("colorId") Integer colorId,
                           @Param("sizeId") Integer sizeId,
                           @Param("fabricTypeId") Integer fabricTypeId,
                           Pageable pageable);
    
    @Query("from ProductDetail b where b.product.id=:productId and b.color.id=:colorId and b.size.id=:sizeId and b.fabricType.id=:fabricTypeId")
    ProductDetail findByColorAndSize(@Param("productId") Integer productId, @Param("colorId") Integer colorId, @Param("sizeId")  Integer sizeId, @Param("fabricTypeId")  Integer fabricTypeId);

    @Query("select nvl(p.storageQty, 0) from ProductDetail p where p.product.id=:productId and p.color.id=:colorId and p.size.id=:sizeId")
    Integer findQuantityBySizeOfEachColor(@Param("productId") Integer productId, @Param("colorId") Integer colorId, @Param("sizeId") Integer sizeId);

    @Query("select SUM(nvl(p.soldQty, 0)) as totalQtySell from ProductDetail p where p.product.id=:productId")
    Integer findTotalQtySell(@Param("productId") Integer productId);

    @Modifying
    @Query("update ProductDetail p set p.storageQty = (p.storageQty + :soldQty) where p.id=:productVariantId")
    void updateQuantityIncrease(@Param("soldQty") Integer soldQty, @Param("productVariantId") Integer productVariantId);

    @Modifying
    @Query("update ProductDetail p set p.storageQty = (p.storageQty - :soldQty), p.soldQty = (p.soldQty + :soldQty) where p.id=:productVariantId")
    void updateQuantityDecrease(@Param("soldQty") Integer soldQty, @Param("productVariantId") Integer productVariantId);

    @Modifying
    @Query("update ProductDetail p set p.originalPrice=:originalPrice, p.discountPrice=:discountPrice where p.id=:productVariantId")
    void updatePrice(@Param("originalPrice") BigDecimal originalPrice, @Param("discountPrice") BigDecimal discountPrice, @Param("productVariantId") Integer productVariantId);

    @Query("select sum(p.storageQty) from ProductDetail p where p.status = 'A'")
    Integer countTotalQuantity();
}