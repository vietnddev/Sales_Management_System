package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.LoaiKichCo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoaiKichCoService {

    List<LoaiKichCo> findAll();

    LoaiKichCo findById(int id);

    String save(LoaiKichCo loaiKichCo);

    String update(LoaiKichCo loaiKichCo, int id);

    String delete(int id);
}