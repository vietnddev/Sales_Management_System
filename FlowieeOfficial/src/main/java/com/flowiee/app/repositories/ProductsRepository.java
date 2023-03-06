package com.flowiee.app.repositories;

import com.flowiee.app.model.sales.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
}
