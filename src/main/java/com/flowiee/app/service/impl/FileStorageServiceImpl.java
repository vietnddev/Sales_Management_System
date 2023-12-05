package com.flowiee.app.service.impl;

import com.flowiee.app.entity.Account;
import com.flowiee.app.entity.Document;
import com.flowiee.app.entity.FileStorage;
import com.flowiee.app.entity.Product;
import com.flowiee.app.entity.ProductVariant;
import com.flowiee.app.repository.FileStorageRepository;
import com.flowiee.app.model.role.SystemModule;
import com.flowiee.app.service.ProductService;
import com.flowiee.app.service.ProductVariantService;
import com.flowiee.app.service.DocumentService;
import com.flowiee.app.service.FileStorageService;
import com.flowiee.app.service.AccountService;

import com.flowiee.app.utils.AppConstants;
import com.flowiee.app.utils.CommonUtil;
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
    private ProductVariantService productVariantService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private FileStorageService fileService;

    @Override
    public List<FileStorage> findAll() {
        return null;
    }

    @Override
    public FileStorage findById(Integer fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }

    @Override
    public String save(FileStorage entity) {
        return null;
    }

    @Override
    public String update(FileStorage entity, Integer entityId) {
        return null;
    }

    @Override
    public FileStorage findFileIsActiveOfDocument(int documentId) {
        FileStorage fileReturn = fileRepository.findFileIsActiveOfDocument(documentId, true);
        if (fileReturn != null) {
            return fileReturn;
        }
        return new FileStorage();
    }

    @Override
    public FileStorage findImageActiveOfSanPham(int sanPhamId) {
        return fileRepository.findImageActiveOfSanPham(sanPhamId, true);
    }

    @Override
    public FileStorage findImageActiveOfSanPhamBienThe(int sanPhamBienTheId) {
        return fileRepository.findImageActiveOfSanPhamBienThe(sanPhamBienTheId, true);
    }

    @Override
    public String setImageActiveOfSanPham(Integer sanPhamId, Integer imageId) {
        //Bỏ image default hiện tại
        FileStorage imageActiving = fileRepository.findImageActiveOfSanPham(sanPhamId, true);
        if (imageActiving != null) {
            imageActiving.setActive(false);
            fileRepository.save(imageActiving);
        }

        //Active lại image theo id được truyền vào
        FileStorage imageToActive = fileRepository.findById(imageId).orElse(null);
        if (imageToActive != null) {
            imageToActive.setActive(true);
            fileRepository.save(imageToActive);
            return "OK";
        }
        return "NOK";
    }

    @Override
    public String setImageActiveOfBienTheSanPham(Integer bienTheSanPhamId, Integer imageId) {
        //Bỏ image default hiện tại
        FileStorage imageActiving = fileRepository.findImageActiveOfSanPhamBienThe(bienTheSanPhamId, true);
        if (imageActiving != null) {
            imageActiving.setActive(false);
            fileRepository.save(imageActiving);
        }

        //Active lại image theo id được truyền vào
        FileStorage imageToActive = fileRepository.findById(imageId).orElse(null);
        if (imageToActive != null) {
            imageToActive.setActive(true);
            fileRepository.save(imageToActive);
            return "OK";
        }
        return "NOK";
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
    public String saveImageSanPham(MultipartFile fileUpload, int sanPhamId) throws IOException {
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(SystemModule.PRODUCT.name());
        fileInfo.setTenFileGoc(fileUpload.getOriginalFilename());
        fileInfo.setTenFileKhiLuu(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setKichThuocFile(fileUpload.getSize());
        fileInfo.setExtension(CommonUtil.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(CommonUtil.getPathDirectoty(SystemModule.PRODUCT).substring(CommonUtil.getPathDirectoty(SystemModule.PRODUCT).indexOf("uploads")));
        fileInfo.setProduct(new Product(sanPhamId));
        fileInfo.setAccount(new Account(accountService.findIdByUsername(CommonUtil.getCurrentAccountUsername())));
        fileInfo.setActive(false);
        fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtil.getPathDirectoty(SystemModule.PRODUCT) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return "OK";
    }

    @Override
    @Transactional
    public String saveImageBienTheSanPham(MultipartFile fileUpload, int bienTheId) throws IOException {
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(SystemModule.PRODUCT.name());
        fileInfo.setTenFileGoc(fileUpload.getOriginalFilename());
        fileInfo.setTenFileKhiLuu(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setKichThuocFile(fileUpload.getSize());
        fileInfo.setExtension(CommonUtil.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(CommonUtil.getPathDirectoty(SystemModule.PRODUCT).substring(CommonUtil.getPathDirectoty(SystemModule.PRODUCT).indexOf("uploads")));
        //
        ProductVariant productVariant = productVariantService.findById(bienTheId);
        fileInfo.setProductVariant(productVariant);
        fileInfo.setProduct(productVariant.getProduct());
        fileInfo.setAccount(new Account(CommonUtil.getCurrentAccountId()));
        fileInfo.setActive(false);
        fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtil.getPathDirectoty(SystemModule.PRODUCT) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return "OK";
    }

    @Override
    public String saveFileOfDocument(MultipartFile fileUpload, Integer documentId) throws IOException {
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(SystemModule.STORAGE.name());
        fileInfo.setTenFileGoc(fileUpload.getOriginalFilename());
        fileInfo.setTenFileKhiLuu(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setKichThuocFile(fileUpload.getSize());
        fileInfo.setExtension(CommonUtil.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(CommonUtil.getPathDirectoty(SystemModule.STORAGE).substring(CommonUtil.getPathDirectoty(SystemModule.STORAGE).indexOf("uploads")));
        fileInfo.setDocument(new Document(documentId));
        fileInfo.setAccount(accountService.findCurrentAccount());
        fileInfo.setActive(true);
        fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtil.getPathDirectoty(SystemModule.STORAGE) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return "OK";
    }

    @Override
    public String saveFileOfImport(MultipartFile fileImport, FileStorage fileInfo) throws IOException {
        fileRepository.save(fileInfo);
        fileInfo.setTenFileKhiLuu("I_" + fileInfo.getTenFileKhiLuu());
        fileImport.transferTo(Paths.get(CommonUtil.getPathDirectoty(fileInfo.getModule()) + "/" + fileInfo.getTenFileKhiLuu()));
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
        fileInfo.setModule(SystemModule.STORAGE.name());
        fileInfo.setTenFileGoc(fileUpload.getOriginalFilename());
        fileInfo.setTenFileKhiLuu(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setKichThuocFile(fileUpload.getSize());
        fileInfo.setExtension(CommonUtil.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(CommonUtil.getPathDirectoty(SystemModule.STORAGE).substring(CommonUtil.getPathDirectoty(SystemModule.STORAGE).indexOf("uploads")));
        fileInfo.setDocument(new Document(documentId));
        fileInfo.setAccount(accountService.findCurrentAccount());
        fileInfo.setActive(true);
        fileRepository.save(fileInfo);

        Path path = Paths.get(CommonUtil.getPathDirectoty(SystemModule.STORAGE) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return "OK";
    }

    @Override
    public String changeImageSanPham(MultipartFile fileAttached, int fileId) {
        Long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileToChange = this.findById(fileId);
        //Delete file vật lý cũ
        try {
            File file = new File(CommonUtil.rootPath + "/" + fileToChange.getDirectoryPath() + "/" + fileToChange.getTenFileKhiLuu());
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            logger.error("File cần change không tồn tại!", e.getCause().getMessage());
        }
        //Update thông tin file mới
        fileToChange.setTenFileGoc(fileAttached.getOriginalFilename());
        fileToChange.setTenFileKhiLuu(currentTime + "_" + fileAttached.getOriginalFilename());
        fileToChange.setKichThuocFile(fileAttached.getSize());
        fileToChange.setExtension(CommonUtil.getExtension(fileAttached.getOriginalFilename()));
        fileToChange.setContentType(fileAttached.getContentType());
        fileToChange.setDirectoryPath(CommonUtil.getPathDirectoty(SystemModule.PRODUCT).substring(CommonUtil.getPathDirectoty(SystemModule.PRODUCT).indexOf("uploads")));
        fileToChange.setAccount(new Account(CommonUtil.getCurrentAccountId()));
        fileRepository.save(fileToChange);

        //Lưu file mới vào thư mục chứa file upload
        try {
            Path path = Paths.get(CommonUtil.getPathDirectoty(SystemModule.PRODUCT) + "/" + currentTime + "_" + fileAttached.getOriginalFilename());
            fileAttached.transferTo(path);
        } catch (Exception e) {
            logger.error("Lưu file change vào thư mục chứa file upload thất bại!", e.getCause().getMessage());
        }

        return "OK";
    }

    @Override
    public String delete(Integer fileId) {
        FileStorage fileStorage = fileRepository.findById(fileId).orElse(null);
        fileRepository.deleteById(fileId);
        //Xóa file trên ổ cứng
        File file = new File(CommonUtil.rootPath + "/" + fileStorage.getDirectoryPath() + "/" + fileStorage.getTenFileKhiLuu());
        System.out.println("Path of file in dic" + file.getPath());
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Xóa thành công file in directory!");
                return "OK";
            } else {
                System.out.println("Xóa thất bại file in directory!");
                return "Xóa file NOK";
            }
        }
        return "OK";
    }
}