package com.flowiee.pms.service.system;

import com.flowiee.pms.base.BaseService;
import org.springframework.web.multipart.MultipartFile;

import com.flowiee.pms.entity.system.FileStorage;

import java.io.IOException;

public interface FileStorageService extends BaseService<FileStorage> {
    String saveFileOfImport(MultipartFile fileImport, FileStorage fileInfo) throws IOException;
}