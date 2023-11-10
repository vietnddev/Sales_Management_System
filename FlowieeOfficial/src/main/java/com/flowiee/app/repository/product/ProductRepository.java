package com.flowiee.app.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.app.entity.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
