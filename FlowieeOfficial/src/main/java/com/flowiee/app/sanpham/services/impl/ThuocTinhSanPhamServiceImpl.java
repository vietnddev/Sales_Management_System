package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.ThuocTinhSanPham;
import com.flowiee.app.sanpham.repository.ThuocTinhSanPhamRepository;
import com.flowiee.app.sanpham.services.ThuocTinhSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThuocTinhSanPhamServiceImpl implements ThuocTinhSanPhamService {
    @Autowired
    private ThuocTinhSanPhamRepository productAttributeRepository;

    @Override
    public List<ThuocTinhSanPham> getAllAttributes(int productVariantID){
        BienTheSanPham bienTheSanPham = new BienTheSanPham();
        bienTheSanPham.setId(productVariantID);
        return productAttributeRepository.findByBienTheSanPham(bienTheSanPham);
    }

    @Override
    public void saveAttribute(ThuocTinhSanPham productAttribute){
        productAttributeRepository.save(productAttribute);
    }

    @Override
    public ThuocTinhSanPham findById(int attributeID){
    	return productAttributeRepository.findById(attributeID).orElse(null);
    }

    @Override
    public void deleteAttribute(int attributeID) {
    	productAttributeRepository.deleteById(attributeID);
    }
}