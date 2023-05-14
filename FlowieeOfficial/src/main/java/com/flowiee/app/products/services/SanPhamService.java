package com.flowiee.app.products.services;

import com.flowiee.app.products.entity.SanPham;
import com.flowiee.app.products.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanPhamService {
    @Autowired
    private SanPhamRepository productsRepository;

    public List<SanPham> getAllProducts(){
        return productsRepository.findAll();
    }

    public SanPham findById(int productID){
        return productsRepository.findById(productID).orElse(null);
    }

    public void insertProduct(SanPham sanPham){
        productsRepository.save(sanPham);
    }

    public void update(SanPham sanPham, int id){
        sanPham.setId(id);
        productsRepository.save(sanPham);
    }

    public void deleteProduct(int productID){
        productsRepository.deleteById(productID);
    }
}
