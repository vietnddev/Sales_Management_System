package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.OrderDetailRpt;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.model.dto.OrderDetailDTO;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.sales.OrderExportService;
import com.flowiee.pms.service.sales.OrderQRCodeService;
import com.flowiee.pms.service.sales.OrderService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.FileUtils;
import com.flowiee.pms.utils.ReportUtils;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderExportServiceImpl extends BaseService implements OrderExportService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderQRCodeService orderQRCodeService;

    @Override
    public ResponseEntity<?> exportToExcel(Integer pOrderId, List<Integer> pOrderIds, boolean isExportAll) {
        long exportTime = System.currentTimeMillis();
        String rootPath = FileUtils.excelTemplatePath;
        String templateName = "Template_E_Order.xlsx";
        String fileNameReturn = exportTime + "_ListOfOrders.xlsx";
        Path templateOriginal = Path.of(rootPath + "/" + templateName);
        Path templateTarget = Path.of(rootPath + "/temp/" + exportTime + "_" + templateName);
        XSSFWorkbook workbook = null;
        try {
            File templateToExport = Files.copy(templateOriginal, templateTarget, StandardCopyOption.REPLACE_EXISTING).toFile();
            workbook = new XSSFWorkbook(templateToExport);
            XSSFSheet sheet = workbook.getSheetAt(0);

            List<OrderDTO> listData = orderService.findAll();
            for (int i = 0; i < listData.size(); i++) {
                XSSFRow row = sheet.createRow(i + 4);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(listData.get(i).getCode());
                row.createCell(2).setCellValue(listData.get(i).getOrderTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                row.createCell(3).setCellValue(listData.get(i).getSalesChannelName());
                row.createCell(4).setCellValue(String.valueOf(listData.get(i).getTotalAmountDiscount()));
                row.createCell(5).setCellValue(listData.get(i).getPayMethodName());
                row.createCell(6).setCellValue(listData.get(i).getPaymentStatus() ? "Đã thanh toán" : "Chưa thanh toán");
                row.createCell(7).setCellValue(listData.get(i).getCustomerName());
                row.createCell(8).setCellValue(listData.get(i).getReceiverPhone());
                row.createCell(9).setCellValue(listData.get(i).getReceiverAddress());
                row.createCell(10).setCellValue(listData.get(i).getOrderStatusName());
                row.createCell(11).setCellValue(listData.get(i).getNote());
                for (int j = 0; j <= 11; j++) {
                    row.getCell(j).setCellStyle(FileUtils.setBorder(workbook.createCellStyle()));
                }
            }
            return new ResponseEntity<>(FileUtils.build(workbook), FileUtils.setHeaders(fileNameReturn), HttpStatus.OK);
        } catch (IOException | InvalidFormatException ex) {
            logger.error("An error when export list of orders!", ex);
            throw new AppException(ex);
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                Files.deleteIfExists(templateTarget);
            } catch (IOException e) {
                logger.error("An error when delete template temp after exported data!", e);
            }
        }
    }

    @Override
    public byte[] exportToCSV(Integer pOrderId, List<Integer> pOrderIds, boolean isExportAll, HttpServletResponse response) {
        return new byte[0];
    }

    @Override
    public void printInvoicePDF(Integer pOrderId, List<Integer> pOrderIds, boolean isExportAll, HttpServletResponse response) {
        Optional<OrderDTO> dtoOptional = orderService.findById(pOrderId);
        if (dtoOptional.isEmpty()) {
            throw new BadRequestException();
        }
        OrderDTO dto = dtoOptional.get();

        boolean checkBatch = false;

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        PDFMergerUtility mergePdf = new PDFMergerUtility();
        //Barcode_Image.createImage(order.getId().toString() + ".png", order.getId().toString());
        HashMap<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("customerName", dto.getReceiverName());
        parameterMap.put("customerAddress", dto.getReceiverAddress());
        parameterMap.put("customerPhone", dto.getReceiverPhone());
        parameterMap.put("customerEmail", dto.getReceiverEmail());
        parameterMap.put("totalSubtotal","$ 0");
        parameterMap.put("totalShippingCost","$ 0");
        parameterMap.put("discount","$ " + dto.getAmountDiscount());
        parameterMap.put("totalPayment", dto.getTotalAmountDiscount());
        parameterMap.put("paymentMethod", dto.getPayMethodName());
        parameterMap.put("invoiceNumber", dto.getCode());
        parameterMap.put("orderDate", dto.getOrderTime());
        parameterMap.put("nowDate", new Date());
        FileStorage f = orderQRCodeService.findQRCodeOfOrder(dto.getId());
        parameterMap.put("barcode", Path.of(FileUtils.rootPath + "/" + f.getDirectoryPath() + "/" + f.getStorageName()));
        parameterMap.put("logoPath", CommonUtils.logoPath);

        // orderDetails
        List<OrderDetailRpt> listDetail = new ArrayList<>();
        for (OrderDetailDTO detailDTO : dto.getListOrderDetailDTO()) {
            OrderDetailRpt rpt = new OrderDetailRpt();
            rpt.setProductName(detailDTO.getProductVariantDTO().getVariantName());
            rpt.setUnitPrice(detailDTO.getPrice());
            rpt.setQuantity(detailDTO.getQuantity());
            rpt.setSubTotal(detailDTO.getPrice().multiply(BigDecimal.valueOf(detailDTO.getQuantity())));
            rpt.setNote(detailDTO.getNote());
            listDetail.add(rpt);
        }

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ServletOutputStream servletOutputStream = response.getOutputStream();
            try {
//				JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.font.name", "false");
//				JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.encoding", "UTF-8");
//				JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.default.pdf.embedded", "true");
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", " inline; filename=deliveryNote" + dto.getId() + ".pdf");
                InputStream reportStream = new FileInputStream(ReportUtils.getReportTemplate("Invoice"));
                JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameterMap, new JRBeanCollectionDataSource(listDetail));
                JasperExportManager.exportReportToPdfStream(jasperPrint, (checkBatch) ? byteArrayOutputStream : servletOutputStream);
                if (checkBatch) {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                } else {
                    servletOutputStream.flush();
                    servletOutputStream.close();
                }
            } catch (Exception e) {
                // display stack trace in the browser
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e.printStackTrace(printWriter);
                response.setContentType("text/plain");
                response.getOutputStream().print(stringWriter.toString());
                throw new AppException(e);
            }
        } catch (Exception e) {
            throw new AppException("Exception print PDF: " + e.getMessage());
        }
    }
}