package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.danhmuc.repository.LoaiTaiLieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiTaiLieuService {
    @Autowired
    private LoaiTaiLieuRepository loaiTaiLieuRepository;

    public List<LoaiTaiLieu> findAll() {
        return loaiTaiLieuRepository.findAll();
    }

    public List<LoaiTaiLieu> findAllWhereStatusTrue() {
        return loaiTaiLieuRepository.findByTrangThai(true);
    }

    public LoaiTaiLieu findById(int id) {
        return loaiTaiLieuRepository.findById(id).orElse(null);
    }

    public LoaiTaiLieu findByTen(String ten) {
        return loaiTaiLieuRepository.findByTen(ten);
    }

    public LoaiTaiLieu save(LoaiTaiLieu loaiTaiLieu) {
        if (findByTen(loaiTaiLieu.getTen()) != null) {
            return null;
        }
        return loaiTaiLieuRepository.save(loaiTaiLieu);
    }

    public void update(LoaiTaiLieu loaiSanPham, int id) {
        loaiSanPham.setId(id);
        loaiTaiLieuRepository.save(loaiSanPham);
    }

    public boolean delete(int id) {
        loaiTaiLieuRepository.deleteById(id);
        if (findById(id) == null){
            return true;
        } else {
            return false;
        }
    }
}
