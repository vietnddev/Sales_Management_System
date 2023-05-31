package com.flowiee.app.file.service;

import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.hethong.model.module.SystemModule;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStorageService {

    List<FileStorage> getAllImageSanPham(String module);

    void saveImageSanPham(MultipartFile fileUpload, int sanPhamId) throws IOException;

    void saveImageBienTheSanPham(MultipartFile fileUpload, int bienTheId) throws IOException;

    void save(MultipartFile fileUpload, SystemModule module);

    String delete(int id);
}