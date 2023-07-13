package com.flowiee.app.file.service;

import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.hethong.model.module.SystemModule;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileStorageService {

    List<FileStorage> getAllImageSanPham(String module);

    List<FileStorage> getImageOfSanPham(int sanPhamId);

    List<FileStorage> getImageOfSanPhamBienThe(int bienTheSanPhamId);

    List<FileStorage> getFileOfDocument(int documentId);

    void saveImageSanPham(MultipartFile fileUpload, int sanPhamId) throws IOException;

    void saveImageBienTheSanPham(MultipartFile fileUpload, int bienTheId) throws IOException;

    void saveFileOfDocument(MultipartFile fileUpload, Integer documentId) throws IOException;

    String delete(int id);

    String changeImageSanPham(MultipartFile fileToChange, int fileId);

    FileStorage findById(int fileId);
}