package com.flowiee.app.file.service.impl;

import com.flowiee.app.common.exception.BadRequestException;
import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.file.repository.FileStorageRepository;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.model.module.SystemModule;
import com.flowiee.app.khotailieu.entity.Document;
import com.flowiee.app.khotailieu.service.DocumentService;
import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.SanPham;
import com.flowiee.app.sanpham.services.BienTheSanPhamService;
import com.flowiee.app.sanpham.services.SanPhamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotActiveException;
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
    private SanPhamService sanPhamService;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;
    @Autowired
    private DocumentService documentService;

    @Override
    public FileStorage findById(int fileId) {
        return fileRepository.findById(fileId).orElse(null);
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
    public List<FileStorage> getAllImageSanPham(String module) {
        return fileRepository.findAllImageSanPham(module);
    }

    @Override
    public List<FileStorage> getImageOfSanPham(int sanPhamId) {
        if (sanPhamService.findById(sanPhamId) == null) {
            throw new BadRequestException();
        }
        return fileRepository.findImageOfSanPham(sanPhamId);
    }

    @Override
    public List<FileStorage> getImageOfSanPhamBienThe(int bienTheSanPhamId) {
        if (bienTheSanPhamService.findById(bienTheSanPhamId) == null) {
            throw new BadRequestException();
        }
        return fileRepository.findImageOfSanPhamBienThe(bienTheSanPhamId);
    }

    @Override
    public List<FileStorage> getFileOfDocument(int documentId) {
        if (documentService.findById(documentId) == null) {
            throw new BadRequestException();
        }
        return fileRepository.findFileOfDocument(documentId);
    }

    @Override
    @Transactional
    public String saveImageSanPham(MultipartFile fileUpload, int sanPhamId) throws IOException {
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(SystemModule.SAN_PHAM.name());
        fileInfo.setTenFileGoc(fileUpload.getOriginalFilename());
        fileInfo.setTenFileKhiLuu(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setKichThuocFile(fileUpload.getSize());
        fileInfo.setExtension(FileUtil.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(FileUtil.pathDirectoty(SystemModule.SAN_PHAM).substring(FileUtil.pathDirectoty(SystemModule.SAN_PHAM).indexOf("uploads")));
        fileInfo.setSanPham(SanPham.builder().id(sanPhamId).build());
        fileInfo.setAccount(Account.builder().id(accountService.findIdByUsername(accountService.getUserName())).build());
        fileInfo.setActive(true);
        fileRepository.save(fileInfo);

        Path path = Paths.get(FileUtil.pathDirectoty(SystemModule.SAN_PHAM) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return "OK";
    }

    @Override
    @Transactional
    public String saveImageBienTheSanPham(MultipartFile fileUpload, int bienTheId) throws IOException {
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(SystemModule.SAN_PHAM.name());
        fileInfo.setTenFileGoc(fileUpload.getOriginalFilename());
        fileInfo.setTenFileKhiLuu(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setKichThuocFile(fileUpload.getSize());
        fileInfo.setExtension(FileUtil.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(FileUtil.pathDirectoty(SystemModule.SAN_PHAM).substring(FileUtil.pathDirectoty(SystemModule.SAN_PHAM).indexOf("uploads")));
        //
        BienTheSanPham bienTheSanPham = bienTheSanPhamService.findById(bienTheId);
        fileInfo.setBienTheSanPham(bienTheSanPham);
        fileInfo.setSanPham(bienTheSanPham.getSanPham());
        fileInfo.setAccount(Account.builder().id(accountService.findIdByUsername(accountService.getUserName())).build());
        fileInfo.setActive(true);
        fileRepository.save(fileInfo);

        Path path = Paths.get(FileUtil.pathDirectoty(SystemModule.SAN_PHAM) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return "OK";
    }

    @Override
    public String saveFileOfDocument(MultipartFile fileUpload, Integer documentId) throws IOException {
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(SystemModule.KHO_TAI_LIEU.name());
        fileInfo.setTenFileGoc(fileUpload.getOriginalFilename());
        fileInfo.setTenFileKhiLuu(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setKichThuocFile(fileUpload.getSize());
        fileInfo.setExtension(FileUtil.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(FileUtil.pathDirectoty(SystemModule.KHO_TAI_LIEU).substring(FileUtil.pathDirectoty(SystemModule.KHO_TAI_LIEU).indexOf("uploads")));
        fileInfo.setDocument(Document.builder().id(documentId).build());
        fileInfo.setAccount(accountService.getCurrentAccount());
        fileInfo.setActive(true);
        fileRepository.save(fileInfo);

        Path path = Paths.get(FileUtil.pathDirectoty(SystemModule.KHO_TAI_LIEU) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return "OK";
    }

    @Override
    public String changFileOfDocument(MultipartFile fileUpload, Integer documentId) throws IOException {
        Document document = documentService.findById(documentId);
        if (document == null) {
            throw new NotActiveException();
        }
        //Set inactive cho các version cũ
        List<FileStorage> listDocFile = document.getListDocFile();
        for (FileStorage docFile : listDocFile) {
            docFile.setActive(false);
            fileRepository.save(docFile);
        }
        //Save file mới vào hệ thống
        long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(SystemModule.KHO_TAI_LIEU.name());
        fileInfo.setTenFileGoc(fileUpload.getOriginalFilename());
        fileInfo.setTenFileKhiLuu(currentTime + "_" + fileUpload.getOriginalFilename());
        fileInfo.setKichThuocFile(fileUpload.getSize());
        fileInfo.setExtension(FileUtil.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setDirectoryPath(FileUtil.pathDirectoty(SystemModule.KHO_TAI_LIEU).substring(FileUtil.pathDirectoty(SystemModule.KHO_TAI_LIEU).indexOf("uploads")));
        fileInfo.setDocument(Document.builder().id(documentId).build());
        fileInfo.setAccount(accountService.getCurrentAccount());
        fileInfo.setActive(true);
        fileRepository.save(fileInfo);

        Path path = Paths.get(FileUtil.pathDirectoty(SystemModule.KHO_TAI_LIEU) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);

        return "OK";
    }

    @Override
    public String changeImageSanPham(MultipartFile fileAttached, int fileId) {
        Long currentTime = Instant.now(Clock.systemUTC()).toEpochMilli();
        FileStorage fileToChange = this.findById(fileId);
        if (fileToChange == null) {
            throw new NotFoundException();
        }
        //Delete file vật lý cũ
        try {
            File file = new File(FileUtil.rootPath + fileToChange.getDirectoryPath() + "/" + fileToChange.getTenFileKhiLuu());
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
        fileToChange.setExtension(FileUtil.getExtension(fileAttached.getOriginalFilename()));
        fileToChange.setContentType(fileAttached.getContentType());
        fileToChange.setDirectoryPath(FileUtil.pathDirectoty(SystemModule.SAN_PHAM).substring(FileUtil.pathDirectoty(SystemModule.SAN_PHAM).indexOf("uploads")));
        fileToChange.setAccount(Account.builder().id(accountService.findIdByUsername(accountService.getUserName())).build());
        fileRepository.save(fileToChange);

        //Lưu file mới vào thư mục chứa file upload
        try {
            Path path = Paths.get(FileUtil.pathDirectoty(SystemModule.SAN_PHAM) + "/" + currentTime + "_" + fileAttached.getOriginalFilename());
            fileAttached.transferTo(path);
        } catch (Exception e) {
            logger.error("Lưu file change vào thư mục chứa file upload thất bại!", e.getCause().getMessage());
        }

        return "OK";
    }

    @Override
    public String delete(int id) {
        FileStorage fileStorage = fileRepository.findById(id).orElse(null);
        if (fileStorage == null) {
            throw new NotFoundException();
        }
        fileRepository.deleteById(id);
        //Xóa file trên ổ cứng
        File file = new File(FileUtil.rootPath + fileStorage.getDirectoryPath() + "/" + fileStorage.getTenFileKhiLuu());
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