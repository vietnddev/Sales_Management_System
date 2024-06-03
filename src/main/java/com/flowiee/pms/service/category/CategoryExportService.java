package com.flowiee.pms.service.category;

public interface CategoryExportService {
    byte[] exportTemplate(String categoryType);

    byte[] exportData(String categoryType);
}