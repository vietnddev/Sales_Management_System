package com.flowiee.app.file.service;

import com.flowiee.app.file.entity.FileStorage;
import com.flowiee.app.system.module.SystemModule;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {

    List<FileStorage> getAllImage();

    void save(MultipartFile fileUpload, SystemModule module);

    void deleteFiles(int fileID);
}