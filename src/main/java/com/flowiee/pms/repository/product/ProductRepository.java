package com.flowiee.pms.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.pms.entity.product.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("select distinct p " +
           "from Product p " +
           "left join ProductDetail pd on pd.product.id = p.id " +
           "where 1=1 " +
           "and (:txtSearch is null or p.productName like %:txtSearch%) " +
           "and (:productTypeId is null or p.productType.id=:productTypeId) " +
           "and (:brandId is null or p.brand.id=:brandId) " +
           "and (:colorId is null or pd.color.id=:colorId) " +
           "and (:sizeId is null or pd.size.id=:sizeId) " +
           "and (:unitId is null or p.unit.id=:unitId) " +
           "and (:status is null or p.status=:status)")
    Page<Product> findAll(@Param("txtSearch") String txtSearch,
                          @Param("brandId") Integer brandId,
                          @Param("productTypeId") Integer productTypeId,
                          @Param("colorId") Integer colorId,
                          @Param("sizeId") Integer sizeId,
                          @Param("unitId") Integer unitId,
                          @Param("status") String status,
                          Pageable pageable);

    @Query("select p.id, p.productName from Product p where p.status=:status")
    List<Object[]> findIdAndName(@Param("status") String status);
}