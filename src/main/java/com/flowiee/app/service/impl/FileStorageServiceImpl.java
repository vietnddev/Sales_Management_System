package com.flowiee.app.service.impl;

import com.flowiee.app.entity.*;
import com.flowiee.app.exception.BadRequestException;
import com.flowiee.app.repository.FileStorageRepository;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.service.DocumentService;
import com.flowiee.app.service.FileStorageService;
import com.flowiee.app.service.AccountService;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtils;
import com.flowiee.app.utils.MessageUtils;
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

    @Autowired
    private AccountService accountService;
    @Autowired
    private FileStorageRepository fileRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private DocumentService documentService;

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
    public FileStorage findFileIsActiveOfDocument(int documentId) {
        return fileRepository.findFileIsActiveOfDocument(documentId, true);
    }

    @Override
    public FileStorage findImageActiveOfSanPham(int sanPhamId) {
        return fileRepository.findImageActiveOfSanPham(sanPhamId, true);
    }

    @Override
    public FileStorage findImageActiveOfSanPhamBienThe(int productVariantId) {
        return fileRepository.findImageActiveOfSanPhamBienThe(productVariantId, true);
    }

    @Override
    public FileStorage setImageActiveOfSanPham(Integer productId, Integer imageId) {
        FileStorage imageToActive = fileRepository.findById(imageId).orElse(null);
        if (imageToActive == null) {
            throw new BadRequestException();
        }
        //Bỏ image default hiện tại
        FileStorage imageActiving = fileRepository.findImageActiveOfSanPham(productId, true);
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
        FileStorage imageActivating = fileRepository.findImageActiveOfSanPhamBienThe(productVariantId, true);
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
        fileInfo.setModule(AppConstants.SYSTEM_MODULE.PRODUCT.name());
        fileInfo.setOriginalName(imageQRCodeName);
        fileInfo.setCustomizeName(imageQRCodeName);
        fileInfo.setStorageName(currentTime + "_" + imageQRCodeName);
        //fileInfo.setKichThuocFile();
        fileInfo.setExtension(AppConstants.FILE_EXTENSION.PNG.getLabel());
        fileInfo.setContentType(null);
        fileInfo.setDirectoryPath(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.PRODUCT).substring(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.PRODUCT).indexOf("uploads")));
        fileInfo.setProduct(null);
        fileInfo.setOrder(new Order(orderId));
        fileInfo.setAccount(new Account(CommonUtils.getCurrentAccountId()));
        fileInfo.setActive(false);
        fileRepository.save(fileInfo);

        String data = "http://192.168.120.199:8085/don-hang/" + orderId;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);

        // Write to file image
        Path path = Paths.get(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.PRODUCT) + "/" + currentTime + "_" + imageQRCodeName);
        MatrixToImageWriter.writeToPath(matrix, "PNG", path);

        return "OK";
    }

    @Override
    public FileStorage findQRCodeOfOrder(int orderId) {
        return fileRepository.findQRCodeOfOrder(orderId);
    }

    @Override
    public List<FileStorage> getAllImageSanPham(String module) {
        return fileRepository.findAllImageSanPham(module);
    }

    @Override
    public List<FileStorage> getImageOfSanPham(int sanPhamId) {
        return fileRepository.findImageOfSanPham(sanPhamId);
    }

    @Override
    public List<FileStorage> getImageOfSanPhamBienThe(int bienTheSanPhamId) {
        return fileRepository.findImageOfSanPhamBienThe(bienTheSanPhamId);
    }

    @Override
    public List<FileStorage> getFileOfDocument(int documentId) {
        return fileRepository.findFileOfDocument(documentId);
    }

    @Override
    @Transactional
    public FileStorage saveImageSanPham(MultipartFile fileUpload, int sanPhamId) throws IOException {
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(AppConstants.SYSTEM_MODULE.PRODUCT.name());
        fileInfo.setOriginalName(fileUpload.getOriginalFilename());
        fileInfo.setCustomizeName(fileUpload.getOriginalFilename());
        fileInfo.setStorageName(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setFileSize(fileUpload.getSize());
        fileInfo.setExtension(CommonUtils.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.PRODUCT).substring(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.PRODUCT).indexOf("uploads")));
        fileInfo.setProduct(new Product(sanPhamId));
        fileInfo.setAccount(new Account(CommonUtils.getCurrentAccountId()));
        fileInfo.setActive(false);
        FileStorage imageSaved = fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.PRODUCT) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return imageSaved;
    }

    @Override
    @Transactional
    public FileStorage saveImageBienTheSanPham(MultipartFile fileUpload, int bienTheId) throws IOException {
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(AppConstants.SYSTEM_MODULE.PRODUCT.name());
        fileInfo.setOriginalName(fileUpload.getOriginalFilename());
        fileInfo.setCustomizeName(fileUpload.getOriginalFilename());
        fileInfo.setStorageName(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setFileSize(fileUpload.getSize());
        fileInfo.setExtension(CommonUtils.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.PRODUCT).substring(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.PRODUCT).indexOf("uploads")));
        //
        ProductVariant productVariant = productService.findProductVariantById(bienTheId);
        fileInfo.setProductVariant(productVariant);
        fileInfo.setProduct(productVariant.getProduct());
        fileInfo.setAccount(new Account(CommonUtils.getCurrentAccountId()));
        fileInfo.setActive(false);
        FileStorage imageSaved = fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.PRODUCT) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return imageSaved;
    }

    @Override
    public FileStorage saveFileOfDocument(MultipartFile fileUpload, Integer documentId) throws IOException {
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(AppConstants.SYSTEM_MODULE.STORAGE.name());
        fileInfo.setOriginalName(fileUpload.getOriginalFilename());
        fileInfo.setCustomizeName(fileUpload.getOriginalFilename());
        fileInfo.setStorageName(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setFileSize(fileUpload.getSize());
        fileInfo.setExtension(CommonUtils.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.STORAGE).substring(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.STORAGE).indexOf("uploads")));
        fileInfo.setDocument(new Document(documentId));
        fileInfo.setAccount(accountService.findCurrentAccount());
        fileInfo.setActive(true);
        FileStorage fileSaved = fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.STORAGE) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return fileSaved;
    }

    @Override
    public String saveFileOfImport(MultipartFile fileImport, FileStorage fileInfo) throws IOException {
        fileRepository.save(fileInfo);
        fileInfo.setStorageName("I_" + fileInfo.getStorageName());
        fileImport.transferTo(Paths.get(CommonUtils.getPathDirectory(fileInfo.getModule()) + "/" + fileInfo.getStorageName()));
        return "OK";
    }

    @Override
    public String changFileOfDocument(MultipartFile fileUpload, Integer documentId) throws IOException {
        Document document = documentService.findById(documentId);
        //Set inactive cho các version cũ
        List<FileStorage> listDocFile = document.getListDocFile();
        for (FileStorage docFile : listDocFile) {
            docFile.setActive(false);
            fileRepository.save(docFile);
        }
        //Save file mới vào hệ thống
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(AppConstants.SYSTEM_MODULE.STORAGE.name());
        fileInfo.setOriginalName(fileUpload.getOriginalFilename());
        fileInfo.setCustomizeName(fileUpload.getOriginalFilename());
        fileInfo.setStorageName(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setFileSize(fileUpload.getSize());
        fileInfo.setExtension(CommonUtils.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.STORAGE).substring(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.STORAGE).indexOf("uploads")));
        fileInfo.setDocument(new Document(documentId));
        fileInfo.setAccount(accountService.findCurrentAccount());
        fileInfo.setActive(true);
        fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.STORAGE) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

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
        fileToChange.setDirectoryPath(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.PRODUCT).substring(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.PRODUCT).indexOf("uploads")));
        fileToChange.setAccount(new Account(CommonUtils.getCurrentAccountId()));
        FileStorage imageSaved = fileRepository.save(fileToChange);

        //Lưu file mới vào thư mục chứa file upload
        try {
            Path path = Paths.get(CommonUtils.getPathDirectory(AppConstants.SYSTEM_MODULE.PRODUCT) + "/" + currentTime + "_" + fileAttached.getOriginalFilename());
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