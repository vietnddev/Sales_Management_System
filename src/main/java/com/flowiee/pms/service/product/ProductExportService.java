package com.flowiee.pms.service.product;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductExportService {
    ResponseEntity<?> exportToExcel(Integer pProductId, List<Integer> pProductIds, boolean isExportAll);

    byte[] exportToCSV(Integer pProductId, List<Integer> pProductIds, boolean isExportAll);

    byte[] exportToPDF(Integer pProductId, List<Integer> pProductIds, boolean isExportAll);
}