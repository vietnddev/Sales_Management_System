package com.flowiee.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("from Product p " +
           //"left join ProductVariant pv on pv.product.id = p.id " +
           "where 1=1 " +
           "and (:txtSearch is null or p.tenSanPham like %:txtSearch%) " +
           //"and (:productTypeId = 0 or p.productType.id=:productTypeId) " +
           //"and (:brandId is null or p.brand.id=:brandId) " +
           //"and (:colorId = 0 or pv.color.id=:colorId) " +
           //"and (:sizeId = 0 or pv.size.id=:sizeId) " +
           "and (:status is null or p.status=:status)")
    Page<Product> findAll(@Param("txtSearch") String txtSearch,
                          //@Param("brandId") Integer brandId,
                          //@Param("productTypeId") Integer productTypeId,
                          @Param("status") String status,
                          //@Param("colorId") Integer colorId,
                          //@Param("sizeId") Integer sizeId,
                          Pageable pageable);
	
    @Query("from Product p where p.productType.id=:productTypeId")
    List<Product> findByProductType(@Param("productTypeId") Integer productTypeId);

    @Query("from Product p where p.unit.id=:unitId")
    List<Product> findByUnit(@Param("unitId") Integer unitId);

    @Query("from Product p where p.brand.id=:brandId")
    List<Product> findByBrand(@Param("brandId") Integer brandId);

    @Query("select p.id, p.tenSanPham from Product p where p.status=:status")
    List<Object[]> findIdAndName(@Param("status") String status);
}