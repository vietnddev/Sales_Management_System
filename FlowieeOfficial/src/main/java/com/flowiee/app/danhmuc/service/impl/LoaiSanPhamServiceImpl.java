package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.danhmuc.entity.LoaiSanPham;
import com.flowiee.app.danhmuc.repository.LoaiSanPhamRepository;
import com.flowiee.app.danhmuc.service.LoaiSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class LoaiSanPhamServiceImpl implements LoaiSanPhamService {
    @Autowired
    private LoaiSanPhamRepository loaiSanPhamRepository;

    @Override
    public List<LoaiSanPham> findAll() {
        return loaiSanPhamRepository.findAll();
    }

    @Override
    public LoaiSanPham findById(int id) {
        return loaiSanPhamRepository.findById(id).orElse(null);
    }

    @Override
    public LoaiSanPham findByTen(String tenLoai) {
        return loaiSanPhamRepository.findByTen(tenLoai);
    }

    @Override
    public LoaiSanPham save(LoaiSanPham loaiSanPham) {
        if (findByTen(loaiSanPham.getTenLoai()) != null) {
            return null;
        }
        return loaiSanPhamRepository.save(loaiSanPham);
    }

    @Override
    public void update(LoaiSanPham loaiSanPham, int id) {
        loaiSanPham.setId(id);
        loaiSanPhamRepository.save(loaiSanPham);
    }

    @Override
    public boolean delete(int id) {
        loaiSanPhamRepository.deleteById(id);
        if (findById(id) == null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String importData(MultipartFile fileImport) {
        return null;
    }

    @Override
    public byte[] exportData() {
        return new byte[0];
    }
}