package com.flowiee.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("from Product p "
			+ "where (:productTypeId is null or p.productType.id=:productTypeId) "
			+ "and (:brandId is null or p.brand.id=:brandId) "
			+ "and (:status is null or p.status=:status)")
    List<Product> findAll(@Param("productTypeId") Integer productTypeId, @Param("brandId") Integer brandId, @Param("status") String status);
	
    @Query("from Product p where p.productType.id=:productTypeId")
    List<Product> findByProductType(@Param("productTypeId") Integer productTypeId);

    @Query("from Product p where p.unit.id=:unitId")
    List<Product> findByUnit(@Param("unitId") Integer unitId);

    @Query("from Product p where p.brand.id=:brandId")
    List<Product> findByBrand(@Param("brandId") Integer brandId);
}