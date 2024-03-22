package com.flowiee.pms.service.product;

import java.util.List;

public interface ProductExportService {
    byte[] exportToExcel(Integer pProductId, List<Integer> pProductIds, boolean isExportAll);

    byte[] exportToCSV(Integer pProductId, List<Integer> pProductIds, boolean isExportAll);

    byte[] exportToPDF(Integer pProductId, List<Integer> pProductIds, boolean isExportAll);
}