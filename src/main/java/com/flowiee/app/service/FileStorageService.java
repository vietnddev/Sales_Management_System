package com.flowiee.app.service;

import com.flowiee.app.base.BaseService;
import com.google.zxing.WriterException;
import org.springframework.web.multipart.MultipartFile;

import com.flowiee.app.entity.FileStorage;

import java.io.IOException;
import java.util.List;

public interface FileStorageService extends BaseService<FileStorage> {

    List<FileStorage> getAllImageSanPham(String module);

    List<FileStorage> getImageOfSanPham(int sanPhamId);

    List<FileStorage> getImageOfSanPhamBienThe(int bienTheSanPhamId);

    List<FileStorage> getFileOfDocument(int documentId);

    FileStorage saveImageSanPham(MultipartFile fileUpload, int sanPhamId) throws IOException;

    FileStorage saveImageBienTheSanPham(MultipartFile fileUpload, int bienTheId) throws IOException;

    String saveFileOfDocument(MultipartFile fileUpload, Integer documentId) throws IOException;

    String saveFileOfImport(MultipartFile fileImport, FileStorage fileInfo) throws IOException;

    String changFileOfDocument(MultipartFile fileUpload, Integer documentId) throws IOException;

    FileStorage changeImageSanPham(MultipartFile fileToChange, int fileId);

    FileStorage findFileIsActiveOfDocument(int documentId);

    FileStorage findImageActiveOfSanPham(int sanPhamId);

    FileStorage findImageActiveOfSanPhamBienThe(int sanPhamBienTheId);

    FileStorage setImageActiveOfSanPham(Integer sanPhamId, Integer imageId);

    FileStorage setImageActiveOfBienTheSanPham(Integer bienTheSanPhamId, Integer imageId);

    String saveQRCodeOfOrder(int orderId) throws IOException, WriterException;

    FileStorage findQRCodeOfOrder(int orderId);
}