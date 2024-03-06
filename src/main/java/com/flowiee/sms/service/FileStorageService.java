package com.flowiee.sms.service;

import com.flowiee.sms.core.BaseService;
import com.google.zxing.WriterException;
import org.springframework.web.multipart.MultipartFile;

import com.flowiee.sms.entity.FileStorage;

import java.io.IOException;
import java.util.List;

public interface FileStorageService extends BaseService<FileStorage> {

    List<FileStorage> getAllImageSanPham(String module);

    List<FileStorage> getImageOfSanPham(int sanPhamId);

    List<FileStorage> getImageOfSanPhamBienThe(int bienTheSanPhamId);

    FileStorage saveImageSanPham(MultipartFile fileUpload, int sanPhamId) throws IOException;

    FileStorage saveImageBienTheSanPham(MultipartFile fileUpload, int bienTheId) throws IOException;

    String saveFileOfImport(MultipartFile fileImport, FileStorage fileInfo) throws IOException;

    FileStorage changeImageSanPham(MultipartFile fileToChange, int fileId);

    FileStorage findImageActiveOfSanPham(int sanPhamId);

    FileStorage findImageActiveOfSanPhamBienThe(int sanPhamBienTheId);

    FileStorage setImageActiveOfSanPham(Integer sanPhamId, Integer imageId);

    FileStorage setImageActiveOfBienTheSanPham(Integer bienTheSanPhamId, Integer imageId);

    String saveQRCodeOfOrder(int orderId) throws IOException, WriterException;

    FileStorage findQRCodeOfOrder(int orderId);
}