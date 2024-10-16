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
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("select distinct p " +
           "from Product p " +
           "left join ProductDetail pd on pd.product.id = p.id " +
           "where 1=1 " +
           "and (:pid is null or p.PID = :pid) " +
           "and (:txtSearch is null or p.productName like %:txtSearch%) " +
           "and (:productTypeId is null or p.productType.id=:productTypeId) " +
           "and (:brandId is null or p.brand.id=:brandId) " +
           "and (:colorId is null or pd.color.id=:colorId) " +
           "and (:sizeId is null or pd.size.id=:sizeId) " +
           "and (:unitId is null or p.unit.id=:unitId) " +
           "and (:status is null or p.status=:status)")
    Page<Product> findAll(@Param("pid") String pid,
                          @Param("txtSearch") String txtSearch,
                          @Param("brandId") Long brandId,
                          @Param("productTypeId") Long productTypeId,
                          @Param("colorId") Long colorId,
                          @Param("sizeId") Long sizeId,
                          @Param("unitId") Long unitId,
                          @Param("status") String status,
                          Pageable pageable);

    @Query("select p.id, p.productName from Product p where p.status=:status")
    List<Object[]> findIdAndName(@Param("status") String status);
}