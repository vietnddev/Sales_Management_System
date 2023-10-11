package com.flowiee.app.danhmuc.service;

import com.flowiee.app.danhmuc.entity.DonViTinh;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DonViTinhService {
    List<DonViTinh> findAll();

    DonViTinh findById(int id);

    String save(DonViTinh donViTinh);

    String update(DonViTinh donViTinh, int id);

    String delete(int id);

    String importData(MultipartFile fileImport);

    byte[] exportTemplate();

    byte[] exportData();
}