package com.flowiee.app.products.services;

import com.flowiee.app.products.entity.BienTheSanPham;
import com.flowiee.app.products.repository.BienTheSanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BienTheSanPhamService {
    @Autowired
    private BienTheSanPhamRepository bienTheSanPhamRepository;

    public List<BienTheSanPham> getListVariantOfProduct(String name, int productID){
        // Lấy danh sách biến thể theo loại biến thể
        //return productVariantRepository.findByNameAndProductID(name, productID);
        return null;
    }

    public void insertVariant(BienTheSanPham productVariant){
        // Thêm mới biển thế sản phẩm
        bienTheSanPhamRepository.save(productVariant);
    }
    
    public Optional<BienTheSanPham> getByVariantID(int productVariantID){
    	return bienTheSanPhamRepository.findById(productVariantID);
    }
    
    public void deteleVariant(int productVariantID) {
        bienTheSanPhamRepository.deleteById(productVariantID);
    }    
}
