package com.flowiee.app.repositories;

import com.flowiee.app.model.sales.Product_Attributes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductAttributeRepository extends JpaRepository<Product_Attributes, Integer> {
    public List<Product_Attributes> findByProductVariantID(int productVariantID);
}
