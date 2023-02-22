package com.flowiee.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flowiee.app.model.sales.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
