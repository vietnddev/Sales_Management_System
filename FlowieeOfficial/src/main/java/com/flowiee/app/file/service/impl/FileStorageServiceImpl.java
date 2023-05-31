package com.flowiee.app.file.service.impl;

import com.flowiee.app.common.exception.NotFoundException;
import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.file.repository.FileStorageRepository;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.hethong.entity.Account;
import com.flowiee.app.hethong.service.AccountService;
import com.flowiee.app.hethong.model.module.SystemModule;
import com.flowiee.app.sanpham.entity.BienTheSanPham;
import com.flowiee.app.sanpham.entity.SanPham;
import com.flowiee.app.sanpham.services.BienTheSanPhamService;
import com.flowiee.app.sanpham.services.SanPhamService;
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
    @Autowired
    private AccountService accountService;
    @Autowired
    private FileStorageRepository fileRepository;
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private BienTheSanPhamService bienTheSanPhamService;

    @Override
    public List<FileStorage> getAllImageSanPham(String module) {
        return fileRepository.findAllImageSanPham(module);
    }

    @Override
    @Transactional
    public void saveImageSanPham(MultipartFile fileUpload, int sanPhamId) throws IOException {
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
        fileRepository.save(fileInfo);

        Path path = Paths.get(FileUtil.pathDirectoty(SystemModule.SAN_PHAM) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);
    }

    @Override
    @Transactional
    public void saveImageBienTheSanPham(MultipartFile fileUpload, int bienTheId) throws IOException {
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
        fileRepository.save(fileInfo);

        Path path = Paths.get(FileUtil.pathDirectoty(SystemModule.SAN_PHAM) + "/" + currentTime + "_" + fileUpload.getOriginalFilename());
        fileUpload.transferTo(path);
    }

    @Override
    public void save(MultipartFile fileUpload, SystemModule module) {
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(module.name());
        fileInfo.setTenFileGoc(fileUpload.getOriginalFilename());
        fileInfo.setKichThuocFile(fileUpload.getSize());
        fileInfo.setExtension(FileUtil.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setAccount(Account.builder().id(accountService.findIdByUsername(accountService.getUserName())).build());
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
        if (file.exists()) {
            if (file.delete()) {
                return "OK";
            } else {
                return "Xóa file NOK";
            }
        }
        return "OK";
    }
}