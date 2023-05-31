package com.flowiee.app.sanpham.services.impl;

import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.SanPham;
import com.flowiee.app.sanpham.repository.BienTheSanPhamRepository;
import com.flowiee.app.sanpham.services.BienTheSanPhamService;
import com.flowiee.app.sanpham.services.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BienTheSanPhamServiceImpl implements BienTheSanPhamService {
    @Autowired
    private BienTheSanPhamRepository bienTheSanPhamRepository;
    @Autowired
    private SanPhamService sanPhamService;

    @Override
    public List<BienTheSanPham> findAll() {
        return bienTheSanPhamRepository.findAll();
    }

    @Override
    public List<BienTheSanPham> getListVariantOfProduct(String loaiBienThe, int sanPhamId) {
        // Lấy danh sách biến thể theo loại biến thể
        return bienTheSanPhamRepository.findListBienTheOfsanPham(loaiBienThe, sanPhamService.findById(sanPhamId));
    }

    @Override
    public void create(BienTheSanPham productVariant){
        bienTheSanPhamRepository.save(productVariant);
    }

    @Override
    public void update(BienTheSanPham productVariant, int id){
        bienTheSanPhamRepository.save(productVariant);
    }

    @Override
    public BienTheSanPham findById(int id){
    	return bienTheSanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public void detele(int productVariantID) {
        bienTheSanPhamRepository.deleteById(productVariantID);
    }    
}