package com.flowiee.app.service;

import org.springframework.web.multipart.MultipartFile;

import com.flowiee.app.entity.FileStorage;

import java.io.IOException;
import java.util.List;

public interface FileStorageService {

    List<FileStorage> getAllImageSanPham(String module);

    List<FileStorage> getImageOfSanPham(int sanPhamId);

    List<FileStorage> getImageOfSanPhamBienThe(int bienTheSanPhamId);

    List<FileStorage> getFileOfDocument(int documentId);

    String saveImageSanPham(MultipartFile fileUpload, int sanPhamId) throws IOException;

    String saveImageBienTheSanPham(MultipartFile fileUpload, int bienTheId) throws IOException;

    String saveFileOfDocument(MultipartFile fileUpload, Integer documentId) throws IOException;

    String saveFileOfImport(MultipartFile fileImport, FileStorage fileInfo) throws IOException;

    String changFileOfDocument(MultipartFile fileUpload, Integer documentId) throws IOException;


    String delete(int id);

    String changeImageSanPham(MultipartFile fileToChange, int fileId);

    FileStorage findById(int fileId);

    FileStorage findFileIsActiveOfDocument(int documentId);

    FileStorage findImageActiveOfSanPham(int sanPhamId);

    FileStorage findImageActiveOfSanPhamBienThe(int sanPhamBienTheId);

    String setImageActiveOfSanPham(Integer sanPhamId, Integer imageId);

    String setImageActiveOfBienTheSanPham(Integer bienTheSanPhamId, Integer imageId);
}