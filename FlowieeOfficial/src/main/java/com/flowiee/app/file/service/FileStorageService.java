package com.flowiee.app.file.service;

import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.nguoidung.entity.TaiKhoan;
import com.flowiee.app.nguoidung.service.AccountService;
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.file.repository.FileStorageRepository;
import com.flowiee.app.system.module.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileStorageService {
    @Autowired
    private FileStorageRepository fileRepository;

    @Autowired
    private AccountService accountService;

    public List<FileStorage> getAllImage(){
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()){
            return fileRepository.findAll();
        }
        return null;
    }

//    public List<FileEntity> getFilesByProductVariant(int productVariantID){
//        return fileRepository.getImageByPVariantID(productVariantID);
//    }

    public void save(MultipartFile fileUpload, SystemModule module) {
        FileStorage fileInfo = new FileStorage();
        fileInfo.setModule(module.name());
        fileInfo.setTenFileGoc(fileUpload.getOriginalFilename());
        fileInfo.setKichThuocFile(fileUpload.getSize());
        fileInfo.setExtension(FileUtil.getExtension(fileUpload.getOriginalFilename()));
        fileInfo.setContentType(fileUpload.getContentType());
        fileInfo.setTaiKhoan(TaiKhoan.builder().id(accountService.findIdByUsername(accountService.getUserName())).build());
        //fileRepository.save();
    }

    public void deleteFiles(int fileID){
        fileRepository.deleteById(fileID);
    }
}
 