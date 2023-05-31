package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.LoaiMauSac;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoaiMauSacService {

    List<LoaiMauSac> findAll();

    LoaiMauSac findById(int id);

    String save(LoaiMauSac loaiMauSac);

    String update(LoaiMauSac loaiMauSac, int id);

    String delete(int id);
}