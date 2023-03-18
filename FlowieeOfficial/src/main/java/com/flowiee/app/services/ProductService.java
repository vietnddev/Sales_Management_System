package com.flowiee.app.services;

import com.flowiee.app.model.sales.Product;
import com.flowiee.app.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Optional<Product> getByID(int id){
        
        return productRepository.findById(id);
    }
   
    public void insertProruct(Product product){
        productRepository.save(product);
    }
 
    public void updateProruct(Product product){
        productRepository.save(product);
    }

    public void deleteProruct(int id){
        productRepository.deleteById(id);
    }
}
