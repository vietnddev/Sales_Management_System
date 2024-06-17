package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.repository.system.FileStorageRepository;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.system.FileStorageService;

import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.FileUtils;
import com.flowiee.pms.utils.constants.ErrorCode;
import com.flowiee.pms.utils.constants.MessageCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class FileStorageServiceImpl extends BaseService implements FileStorageService {
    private FileStorageRepository fileRepository;

    public FileStorageServiceImpl(FileStorageRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public List<FileStorage> findAll() {
        return fileRepository.findAll();
    }

    @Override
    public Optional<FileStorage> findById(Integer fileId) {
        return fileRepository.findById(fileId);
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
    public String saveFileOfImport(MultipartFile fileImport, FileStorage fileInfo) throws IOException {
        fileRepository.save(fileInfo);
        fileInfo.setStorageName("I_" + fileInfo.getStorageName());
        fileImport.transferTo(Paths.get(CommonUtils.getPathDirectory(fileInfo.getModule()) + "/" + fileInfo.getStorageName()));
        return "OK";
    }

    @Override
    public String delete(Integer fileId) {
        Optional<FileStorage> fileStorage = fileRepository.findById(fileId);
        if (fileStorage.isEmpty()) {
            throw new BadRequestException("File not found!");
        }
        fileRepository.deleteById(fileId);
        File file = new File(FileUtils.rootPath + "/" + fileStorage.get().getDirectoryPath() + "/" + fileStorage.get().getStorageName());
        if (file.exists() && file.delete()) {
            return MessageCode.DELETE_SUCCESS.getDescription();
        }
        return String.format(ErrorCode.DELETE_ERROR_OCCURRED.getDescription(), "file");
    }
}