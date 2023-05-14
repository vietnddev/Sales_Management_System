package com.flowiee.app.products.services;

import com.flowiee.app.products.entity.BienTheSanPham;
import com.flowiee.app.products.entity.ThuocTinhSanPham;
import com.flowiee.app.products.repository.ThuocTinhSanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThuocTinhSanPhamService {
    @Autowired
    private ThuocTinhSanPhamRepository productAttributeRepository;

    public List<ThuocTinhSanPham> getAllAttributes(int productVariantID){
        BienTheSanPham bienTheSanPham = new BienTheSanPham();
        bienTheSanPham.setId(productVariantID);
        return productAttributeRepository.findByBienTheSanPham(bienTheSanPham);
    }

    public void saveAttribute(ThuocTinhSanPham productAttribute){
        productAttributeRepository.save(productAttribute);
    }
    
    public Optional<ThuocTinhSanPham> getByAttributeID(int attributeID){
    	return productAttributeRepository.findById(attributeID);
    }
    
    public void deleteAttribute(int attributeID) {
    	productAttributeRepository.deleteById(attributeID);
    }
}
