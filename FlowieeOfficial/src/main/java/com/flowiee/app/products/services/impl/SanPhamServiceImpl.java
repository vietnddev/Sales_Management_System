package com.flowiee.app.products.services.impl;

import com.flowiee.app.products.entity.SanPham;
import com.flowiee.app.products.repository.SanPhamRepository;
import com.flowiee.app.products.services.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepository productsRepository;

    @Override
    public List<SanPham> getAllProducts(){
        return productsRepository.findAll();
    }

    @Override
    public SanPham findById(int productID){
        return productsRepository.findById(productID).orElse(null);
    }

    @Override
    public void insertProduct(SanPham sanPham){
        productsRepository.save(sanPham);
    }

    @Override
    public void update(SanPham sanPham, int id){
        sanPham.setId(id);
        productsRepository.save(sanPham);
    }

    @Override
    public void deleteProduct(int productID){
        productsRepository.deleteById(productID);
    }
}