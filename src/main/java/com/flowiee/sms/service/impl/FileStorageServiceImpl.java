package com.flowiee.sms.service.impl;

import com.flowiee.sms.entity.*;
import com.flowiee.sms.core.exception.BadRequestException;
import com.flowiee.sms.model.MODULE;
import com.flowiee.sms.repository.FileStorageRepository;
import com.flowiee.sms.service.ProductService;
import com.flowiee.sms.service.FileStorageService;

import com.flowiee.sms.utils.AppConstants;
import com.flowiee.sms.utils.CommonUtils;
import com.flowiee.sms.utils.MessageUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    @Autowired private FileStorageRepository fileRepository;
    @Autowired private ProductService productService;

    @Override
    public FileStorage findById(Integer fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }

    @Override
    public FileStorage save(FileStorage entity) {
        return null;
    }

    @Override
    public FileStorage update(FileStorage entity, Integer entityId) {
        return null;
    }

    @Override
    public FileStorage findImageActiveOfSanPham(int sanPhamId) {
        return fileRepository.findImageActiveOfProduct(sanPhamId, true);
    }

    @Override
    public FileStorage findImageActiveOfSanPhamBienThe(int productVariantId) {
        return fileRepository.findImageActiveOfProductDetail(productVariantId, true);
    }

    @Override
    public FileStorage setImageActiveOfSanPham(Integer productId, Integer imageId) {
        FileStorage imageToActive = fileRepository.findById(imageId).orElse(null);
        if (imageToActive == null) {
            throw new BadRequestException();
        }
        //Bỏ image default hiện tại
        FileStorage imageActiving = fileRepository.findImageActiveOfProduct(productId, true);
        if (imageActiving != null) {
            imageActiving.setActive(false);
            fileRepository.save(imageActiving);
        }
        //Active lại image theo id được truyền vào
        imageToActive.setActive(true);
        return fileRepository.save(imageToActive);
    }

    @Override
    public FileStorage setImageActiveOfBienTheSanPham(Integer productVariantId, Integer imageId) {
        FileStorage imageToActive = fileRepository.findById(imageId).orElse(null);
        if (imageToActive == null) {
            throw new BadRequestException();
        }
        //Bỏ image default hiện tại
        FileStorage imageActivating = fileRepository.findImageActiveOfProductDetail(productVariantId, true);
        if (ObjectUtils.isNotEmpty(imageActivating)) {
            imageActivating.setActive(false);
            fileRepository.save(imageActivating);
        }
        //Active lại image theo id được truyền vào
        imageToActive.setActive(true);
        return fileRepository.save(imageToActive);
    }

    @Override
    public String saveQRCodeOfOrder(int orderId) throws IOException, WriterException {
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        String imageQRCodeName = "qrcode_" + orderId + ".png";
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(MODULE.PRODUCT.name());
        fileInfo.setOriginalName(imageQRCodeName);
        fileInfo.setCustomizeName(imageQRCodeName);
        fileInfo.setStorageName(currentTime + "_" + imageQRCodeName);
        //fileInfo.setKichThuocFile();
        fileInfo.setExtension(AppConstants.FILE_EXTENSION.PNG.getLabel());
        fileInfo.setContentType(null);
        fileInfo.setDirectoryPath(CommonUtils.getPathDirectory(MODULE.PRODUCT).substring(CommonUtils.getPathDirectory(MODULE.PRODUCT).indexOf("uploads")));
        fileInfo.setProduct(null);
        fileInfo.setOrder(new Order(orderId));
        fileInfo.setAccount(new Account(CommonUtils.getUserPrincipal().getId()));
        fileInfo.setActive(false);
        fileRepository.save(fileInfo);

        String data = "http://192.168.120.199:8085/don-hang/" + orderId;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);

        // Write to file image
        Path path = Paths.get(CommonUtils.getPathDirectory(MODULE.PRODUCT) + "/" + currentTime + "_" + imageQRCodeName);
        MatrixToImageWriter.writeToPath(matrix, "PNG", path);

        return "OK";
    }

    @Override
    public FileStorage findQRCodeOfOrder(int orderId) {
        return fileRepository.findQRCodeOfOrder(orderId);
    }

    @Override
    public List<FileStorage> getAllImageSanPham(String module) {
        return fileRepository.findAllImageProduct(module);
    }

    @Override
    public List<FileStorage> getImageOfSanPham(int sanPhamId) {
        return fileRepository.findImageOfProduct(sanPhamId);
    }

    @Override
    public List<FileStorage> getImageOfSanPhamBienThe(int bienTheSanPhamId) {
        return fileRepository.findImageOfProductDetail(bienTheSanPhamId);
    }

    @Override
    @Transactional
    public FileStorage saveImageSanPham(MultipartFile fileUpload, int sanPhamId) throws IOException {
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(MODULE.PRODUCT.name());
        fileInfo.setOriginalName(fileUpload.getOriginalFilename());
        fileInfo.setCustomizeName(fileUpload.getOriginalFilename());
        fileInfo.setStorageName(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setFileSize(fileUpload.getSize());
        fileInfo.setExtension(CommonUtils.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(CommonUtils.getPathDirectory(MODULE.PRODUCT).substring(CommonUtils.getPathDirectory(MODULE.PRODUCT).indexOf("uploads")));
        fileInfo.setProduct(new Product(sanPhamId));
        fileInfo.setAccount(new Account(CommonUtils.getUserPrincipal().getId()));
        fileInfo.setActive(false);
        FileStorage imageSaved = fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtils.getPathDirectory(MODULE.PRODUCT) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return imageSaved;
    }

    @Override
    @Transactional
    public FileStorage saveImageBienTheSanPham(MultipartFile fileUpload, int bienTheId) throws IOException {
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(MODULE.PRODUCT.name());
        fileInfo.setOriginalName(fileUpload.getOriginalFilename());
        fileInfo.setCustomizeName(fileUpload.getOriginalFilename());
        fileInfo.setStorageName(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setFileSize(fileUpload.getSize());
        fileInfo.setExtension(CommonUtils.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(CommonUtils.getPathDirectory(MODULE.PRODUCT).substring(CommonUtils.getPathDirectory(MODULE.PRODUCT).indexOf("uploads")));
        //
        ProductDetail productDetail = productService.findProductVariantById(bienTheId);
        fileInfo.setProductDetail(productDetail);
        fileInfo.setProduct(productDetail.getProduct());
        fileInfo.setAccount(new Account(CommonUtils.getUserPrincipal().getId()));
        fileInfo.setActive(false);
        FileStorage imageSaved = fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtils.getPathDirectory(MODULE.PRODUCT) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return imageSaved;
    }

    @Override
    public String saveFileOfImport(MultipartFile fileImport, FileStorage fileInfo) throws IOException {
        fileRepository.save(fileInfo);
        fileInfo.setStorageName("I_" + fileInfo.getStorageName());
        fileImport.transferTo(Paths.get(CommonUtils.getPathDirectory(fileInfo.getModule()) + "/" + fileInfo.getStorageName()));
        return "OK";
    }

    @Transactional
    @Override
    public FileStorage changeImageSanPham(MultipartFile fileAttached, int fileId) {
        Long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileToChange = this.findById(fileId);
        //Delete file vật lý cũ
        try {
            File file = new File(CommonUtils.rootPath + "/" + fileToChange.getDirectoryPath() + "/" + fileToChange.getStorageName());
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            logger.error("File not found!", e);
        }
        //Update thông tin file mới
        fileToChange.setOriginalName(fileAttached.getOriginalFilename());
        fileToChange.setCustomizeName(fileAttached.getOriginalFilename());
        fileToChange.setStorageName(currentTime + "_" + fileAttached.getOriginalFilename());
        fileToChange.setFileSize(fileAttached.getSize());
        fileToChange.setExtension(CommonUtils.getExtension(fileAttached.getOriginalFilename()));
        fileToChange.setContentType(fileAttached.getContentType());
        fileToChange.setDirectoryPath(CommonUtils.getPathDirectory(MODULE.PRODUCT).substring(CommonUtils.getPathDirectory(MODULE.PRODUCT).indexOf("uploads")));
        fileToChange.setAccount(new Account(CommonUtils.getUserPrincipal().getId()));
        FileStorage imageSaved = fileRepository.save(fileToChange);

        //Lưu file mới vào thư mục chứa file upload
        try {
            Path path = Paths.get(CommonUtils.getPathDirectory(MODULE.PRODUCT) + "/" + currentTime + "_" + fileAttached.getOriginalFilename());
            fileAttached.transferTo(path);
        } catch (Exception e) {
            logger.error("Lưu file change vào thư mục chứa file upload thất bại!", e.getCause().getMessage());
        }

        return imageSaved;
    }

    @Override
    public String delete(Integer fileId) {
        FileStorage fileStorage = fileRepository.findById(fileId).orElse(null);
        fileRepository.deleteById(fileId);
        File file = new File(CommonUtils.rootPath + "/" + fileStorage.getDirectoryPath() + "/" + fileStorage.getStorageName());
        if (file.exists() && file.delete()) {
            return MessageUtils.DELETE_SUCCESS;
        }
        return String.format(MessageUtils.DELETE_ERROR_OCCURRED, "file");
    }
}