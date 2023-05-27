package com.flowiee.app.danhmuc.service.impl;

import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.danhmuc.repository.LoaiTaiLieuRepository;
import com.flowiee.app.danhmuc.service.LoaiTaiLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiTaiLieuServiceImpl implements LoaiTaiLieuService {
    @Autowired
    private LoaiTaiLieuRepository loaiTaiLieuRepository;

    @Override
    public List<LoaiTaiLieu> findAll() {
        return loaiTaiLieuRepository.findAll();
    }

    @Override
    public List<LoaiTaiLieu> findAllWhereStatusTrue() {
        return loaiTaiLieuRepository.findByTrangThai(true);
    }

    @Override
    public LoaiTaiLieu findById(int id) {
        return loaiTaiLieuRepository.findById(id).orElse(null);
    }

    @Override
    public LoaiTaiLieu findByTen(String ten) {
        return loaiTaiLieuRepository.findByTen(ten);
    }

    @Override
    public LoaiTaiLieu save(LoaiTaiLieu loaiTaiLieu) {
        if (findByTen(loaiTaiLieu.getTen()) != null) {
            return null;
        }
        return loaiTaiLieuRepository.save(loaiTaiLieu);
    }
    @Override
    public void update(LoaiTaiLieu loaiSanPham, int id) {
        loaiSanPham.setId(id);
        loaiTaiLieuRepository.save(loaiSanPham);
    }

    @Override
    public boolean delete(int id) {
        loaiTaiLieuRepository.deleteById(id);
        if (findById(id) == null){
            return true;
        } else {
            return false;
        }
    }
}