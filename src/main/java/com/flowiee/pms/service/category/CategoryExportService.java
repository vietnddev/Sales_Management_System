package com.flowiee.pms.service.category;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryExportService {
    ResponseEntity<?> exportToExcel(String categoryType, List<Integer> pCategoryIds, boolean isExportAll);
}