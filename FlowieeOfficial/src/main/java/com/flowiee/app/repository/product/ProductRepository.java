package com.flowiee.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("from Product p where p.productType.id=:productTypeId")
    List<Product> findByProductType(Integer productTypeId);

    @Query("from Product p where p.unit.id=:unitId")
    List<Product> findByUnit(Integer unitId);

    @Query("from Product p where p.brand.id=:brandId")
    List<Product> findByBrand(Integer brandId);
}