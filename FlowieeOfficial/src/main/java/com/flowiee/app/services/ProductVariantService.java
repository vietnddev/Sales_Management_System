package com.flowiee.app.services;

import com.flowiee.app.model.sales.Product_Variants;
import com.flowiee.app.repositories.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductVariantService {
    @Autowired
    private ProductVariantRepository productVariantRepository;

    public List<Product_Variants> getListVariantOfProduct(String name, int productID){
        // Lấy danh sách biến thể theo loại biến thể
        return productVariantRepository.findByNameAndProductID(name, productID);
    }

    public void insertVariant(Product_Variants productVariant){
        // Thêm mới biển thế sản phẩm
        productVariantRepository.save(productVariant);
    }
    
    public Optional<Product_Variants> getByVariantID(int productVariantID){
    	return productVariantRepository.findById(productVariantID);
    }
    
    public void deteleVariant(int productVariantID) {
    	productVariantRepository.deleteById(productVariantID);
    }    
}
