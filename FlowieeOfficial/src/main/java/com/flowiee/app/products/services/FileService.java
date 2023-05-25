package com.flowiee.app.products.services;

import com.flowiee.app.nguoidung.service.AccountService;
import com.flowiee.app.file.entity.FileEntity;
import com.flowiee.app.products.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private AccountService accountService;

    public List<FileEntity> getAllImage(){
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()){
            return fileRepository.getAllImage();
        }
        return null;
    }

//    public List<FileEntity> getFilesByProductVariant(int productVariantID){
//        return fileRepository.getImageByPVariantID(productVariantID);
//    }

    public void insertFiles(FileEntity filess) {
        fileRepository.save(filess);
    }

    public void deleteFiles(int fileID){
        fileRepository.deleteById(fileID);
    }
}
 