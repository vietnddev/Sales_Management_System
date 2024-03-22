package com.flowiee.pms.service.sales;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface OrderExportService {
    byte[] exportToExcel(Integer pOrderId, List<Integer> pOrderIds, boolean isExportAll, HttpServletResponse response);

    byte[] exportToCSV(Integer pOrderId, List<Integer> pOrderIds, boolean isExportAll, HttpServletResponse response);

    void exportToPDF(Integer pOrderId, List<Integer> pOrderIds, boolean isExportAll, HttpServletResponse response);
}