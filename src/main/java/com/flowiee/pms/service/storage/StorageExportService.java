package com.flowiee.pms.service.storage;

import org.springframework.http.ResponseEntity;

public interface StorageExportService {
    ResponseEntity<?> exportToExcel(Integer pStorageId);
}