package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.ProductVariant;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository <ProductVariant, Integer>{
    @Query("select " +
           "nvl(p.id, 0) as product_id_0, " +
           "p.tenSanPham as product_name_1, " +
           "nvl(v.id, 0) as product_variant_id_2, " +
           "v.maSanPham as product_variant_code_3, " +
           "v.tenBienThe as product_variant_name_4, " +
           "nvl(c.id, 0) as color_id_5, " +
           "c.name as color_name_6, " +
           "nvl(s.id, 0) as size_id_7, " +
           "s.name as size_name_8, " +
           "nvl(f.id, 0) as fabric_type_id_9, " +
           "f.name as fabric_type_name_10, " +
           "v.soLuongKho as qty_storage_11, " +
           "v.soLuongDaBan as qty_sold_12, " +
           "nvl(g.id, 0) as garment_factory_id_13, " +
           "g.name as garment_factory_name_14, " +
           "nvl(sp.id, 0) as supplier_id_15, " +
           "sp.name as supplier_name_16, " +
           "nvl(ti.id, 0) as ticket_import_id_17, " +
           "ti.title as ticket_import_title_18," +
           "nvl(pr.id, 0) as price_id_19," +
           "pr.giaBan as price_sold_20," +
           "v.trangThai as product_variant_status_21 " +
           "from ProductVariant v " +
           "left join Product p on p.id = v.product.id " +
           "left join GarmentFactory g on g.id = v.garmentFactory.id " +
           "left join Supplier sp on sp.id = v.supplier.id " +
           "left join Price pr on pr.productVariant.id = v.id and pr.status = 'ACTIVE' " +
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
           "order by c.name")
    List<Object[]> findAll(@Param("productId") Integer productId, @Param("ticketImportId") Integer ticketImportId,
                           @Param("colorId") Integer colorId, @Param("sizeId") Integer sizeId, @Param("fabricTypeId") Integer fabricTypeId);
    
    @Query("from ProductVariant b where b.product.id=:productId and b.color.id=:colorId and b.size.id=:sizeId")
    ProductVariant findByColorAndSize(@Param("productId") Integer productId, @Param("colorId") Integer colorId, @Param("sizeId")  Integer sizeId);

    @Query("from ProductVariant b where b.ticketImport.id=:importId")
    List<ProductVariant> findByImportId(@Param("importId") Integer importId);

    @Query("from ProductVariant p where p.fabricType.id=:fabricTypeId")
    List<ProductVariant> findByFabricType(@Param("fabricTypeId") Integer fabricTypeId);

    @Query("from ProductVariant p where p.color.id=:colorId")
    List<ProductVariant> findByColor(@Param("colorId") Integer colorId);

    @Query("from ProductVariant p where p.size.id=:sizeId")
    List<ProductVariant> findBySize(@Param("sizeId") Integer sizeId);

    @Query("select nvl(p.soLuongKho, 0) from ProductVariant p where p.product.id=:productId and p.color.id=:colorId and p.size.id=:sizeId")
    Integer findQuantityBySizeOfEachColor(@Param("productId") Integer productId, @Param("colorId") Integer colorId, @Param("sizeId") Integer sizeId);

    @Query("select SUM(nvl(p.soLuongDaBan, 0)) as totalQtySell from ProductVariant p where p.product.id=:productId")
    Integer findTotalQtySell(@Param("productId") Integer productId);
}