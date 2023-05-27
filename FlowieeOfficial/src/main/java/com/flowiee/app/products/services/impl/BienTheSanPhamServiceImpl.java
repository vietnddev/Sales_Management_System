package com.flowiee.app.products.services.impl;

import com.flowiee.app.products.entity.BienTheSanPham;
import com.flowiee.app.products.repository.BienTheSanPhamRepository;
import com.flowiee.app.products.services.BienTheSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BienTheSanPhamServiceImpl implements BienTheSanPhamService {
    @Autowired
    private BienTheSanPhamRepository bienTheSanPhamRepository;

    @Override
    public List<BienTheSanPham> getListVariantOfProduct(String name, int productID){
        // Lấy danh sách biến thể theo loại biến thể
        return null;
    }

    @Override
    public void insertVariant(BienTheSanPham productVariant){
        // Thêm mới biển thế sản phẩm
        bienTheSanPhamRepository.save(productVariant);
    }

    @Override
    public Optional<BienTheSanPham> getByVariantID(int productVariantID){
    	return bienTheSanPhamRepository.findById(productVariantID);
    }

    @Override
    public void deteleVariant(int productVariantID) {
        bienTheSanPhamRepository.deleteById(productVariantID);
    }    
}