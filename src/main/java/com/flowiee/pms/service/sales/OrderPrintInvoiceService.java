package com.flowiee.pms.service.sales;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface OrderPrintInvoiceService {
    void printInvoicePDF(Long pOrderId, List<Integer> pOrderIds, boolean isExportAll, HttpServletResponse response);
}