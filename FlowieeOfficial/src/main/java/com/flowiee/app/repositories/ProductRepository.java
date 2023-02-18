package com.flowiee.app.repositories;

import com.flowiee.app.model.sales.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
