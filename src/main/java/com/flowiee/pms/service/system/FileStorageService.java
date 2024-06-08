package com.flowiee.pms.service.system;

import com.flowiee.pms.service.BaseCurdService;
import org.springframework.web.multipart.MultipartFile;

import com.flowiee.pms.entity.system.FileStorage;

import java.io.IOException;

public interface FileStorageService extends BaseCurdService<FileStorage> {
    String saveFileOfImport(MultipartFile fileImport, FileStorage fileInfo) throws IOException;
}