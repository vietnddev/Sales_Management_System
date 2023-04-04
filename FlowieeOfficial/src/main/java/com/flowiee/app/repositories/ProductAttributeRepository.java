package com.flowiee.app.repositories;

import com.flowiee.app.model.Product_Attributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<Product_Attributes, Integer> {
    public List<Product_Attributes> findByProductVariantIDOrderBySort(int productVariantID);
}
