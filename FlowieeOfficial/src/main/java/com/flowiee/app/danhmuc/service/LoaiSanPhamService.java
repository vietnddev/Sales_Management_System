package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.LoaiSanPham;
import com.flowiee.app.danhmuc.repository.LoaiSanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiSanPhamService {
    @Autowired
    private LoaiSanPhamRepository loaiSanPhamRepository;

    public List<LoaiSanPham> findAll() {
        return loaiSanPhamRepository.findAll();
    }

    public LoaiSanPham findById(int id) {
        return loaiSanPhamRepository.findById(id).orElse(null);
    }

    public LoaiSanPham findByTen(String tenLoai) {
        return loaiSanPhamRepository.findByTen(tenLoai);
    }

    public LoaiSanPham save(LoaiSanPham loaiSanPham) {
        if (findByTen(loaiSanPham.getTenLoai()) != null) {
            return null;
        }
        return loaiSanPhamRepository.save(loaiSanPham);
    }

    public void update(LoaiSanPham loaiSanPham, int id) {
        loaiSanPham.setId(id);
        loaiSanPhamRepository.save(loaiSanPham);
    }

    public boolean delete(int id) {
        loaiSanPhamRepository.deleteById(id);
        if (findById(id) == null){
            return true;
        } else {
            return false;
        }
    }
}
