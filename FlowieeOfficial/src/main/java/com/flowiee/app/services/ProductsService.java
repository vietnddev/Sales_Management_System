package com.flowiee.app.services;

import com.flowiee.app.model.sales.Products;
import com.flowiee.app.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    public List<Products> getAllProducts(){
        return productsRepository.findAll();
    }

    public Optional<Products> getProductByID(int productID){
        return productsRepository.findById(productID);
    }

    public void insertProduct(Products products){
        productsRepository.save(products);
    }

    public void updateProduct(Products products){
        productsRepository.save(products);
    }

    public void deleteProduct(int productID){
        productsRepository.deleteById(productID);
    }
}
