package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.LoaiTaiLieu;
import com.flowiee.app.danhmuc.repository.LoaiTaiLieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LoaiTaiLieuService {

    List<LoaiTaiLieu> findAll();

    List<LoaiTaiLieu> findAllWhereStatusTrue();

    LoaiTaiLieu findById(int id);

    LoaiTaiLieu findByTen(String ten);

    LoaiTaiLieu save(LoaiTaiLieu loaiTaiLieu);

    void update(LoaiTaiLieu loaiSanPham, int id);

    boolean delete(int id);
}