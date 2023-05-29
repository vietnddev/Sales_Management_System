package com.flowiee.app.file.service.impl;

import com.flowiee.app.common.utils.FileUtil;
import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.file.repository.FileStorageRepository;
import com.flowiee.app.file.service.FileStorageService;
import com.flowiee.app.account.entity.Account;
import com.flowiee.app.account.service.AccountService;
import com.flowiee.app.system.module.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Autowired
    private AccountService accountService;

    @Autowired
    private FileStorageRepository fileRepository;

    @Override
    public List<FileStorage> getAllImage(){
        String username = accountService.getUserName();
        if (username != null && !username.isEmpty()){
            return fileRepository.findAll();
        }
        return null;
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
    public void deleteFiles(int fileID){
        fileRepository.deleteById(fileID);
    }
}