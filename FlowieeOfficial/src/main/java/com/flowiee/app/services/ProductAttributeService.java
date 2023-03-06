package com.flowiee.app.services;

import com.flowiee.app.model.sales.Product_Attributes;
import com.flowiee.app.repositories.ProductAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductAttributeService {
    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    public List<Product_Attributes> getAllAttributes(int productVariantID){
        return productAttributeRepository.findByProductVariantID(productVariantID);
    }

    public void insertAttributes(Product_Attributes productAttribute){
        productAttributeRepository.save(productAttribute);
    }
    
    public Optional<Product_Attributes> getByAttributeID(int attributeID){
    	return productAttributeRepository.findById(attributeID);
    }
    
    public void deleteAttribute(int attributeID) {
    	productAttributeRepository.deleteById(attributeID);
    }
}
