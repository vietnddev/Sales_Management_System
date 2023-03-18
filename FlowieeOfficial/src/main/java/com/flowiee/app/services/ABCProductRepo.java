package com.flowiee.app.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flowiee.app.model.sales.Products;

@Repository
public interface ABCProductRepo extends JpaRepository<Products, Integer>{
	
	List<Products> findAll();
}
