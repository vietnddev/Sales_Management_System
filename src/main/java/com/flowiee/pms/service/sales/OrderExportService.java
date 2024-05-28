package com.flowiee.pms.service.sales;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface OrderExportService {
    ResponseEntity<?> exportToExcel(Integer pOrderId, List<Integer> pOrderIds, boolean isExportAll);

    byte[] exportToCSV(Integer pOrderId, List<Integer> pOrderIds, boolean isExportAll, HttpServletResponse response);

    void printInvoicePDF(Integer pOrderId, List<Integer> pOrderIds, boolean isExportAll, HttpServletResponse response);
}