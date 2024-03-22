package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.exception.BadRequestException;
import com.flowiee.pms.model.OrderDetailRpt;
import com.flowiee.pms.model.dto.OrderDTO;
import com.flowiee.pms.model.dto.OrderDetailDTO;
import com.flowiee.pms.service.product.impl.ProductImageServiceImpl;
import com.flowiee.pms.service.sales.OrderExportService;
import com.flowiee.pms.service.sales.OrderQRCodeService;
import com.flowiee.pms.service.sales.OrderService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.ReportUtils;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Clock;
import java.time.Instant;
import java.util.*;

@Service
public class OrderExportServiceImpl implements OrderExportService {
    Logger logger = LoggerFactory.getLogger(OrderExportServiceImpl.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderQRCodeService orderQRCodeService;

    @Override
    public byte[] exportToExcel(Integer pOrderId, List<Integer> pOrderIds, boolean isExportAll, HttpServletResponse response) {
        String rootPath = "src/main/resources/static/templates/excel/";
        String filePathOriginal = rootPath + "Template_Export_DanhSachDonHang.xlsx";
        String filePathTemp = rootPath + "Template_Export_DanhSachDonHang_" + Instant.now(Clock.systemUTC()).toEpochMilli() + ".xlsx";
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(Files.copy(Path.of(filePathOriginal),
                    Path.of(filePathTemp),
                    StandardCopyOption.REPLACE_EXISTING).toFile());
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<OrderDTO> listData = orderService.findAll();
            for (int i = 0; i < listData.size(); i++) {
                XSSFRow row = sheet.createRow(i + 4);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(listData.get(i).getCode());
                row.createCell(2).setCellValue(listData.get(i).getOrderTime());
                row.createCell(3).setCellValue(listData.get(i).getSalesChannelName());
                row.createCell(4).setCellValue(String.valueOf(listData.get(i).getTotalAmountDiscount()));
                row.createCell(5).setCellValue("");
                row.createCell(6).setCellValue(listData.get(i).getCustomerName());
                row.createCell(7).setCellValue("");//listData.get(i).getKhachHang().getAddress()
                row.createCell(8).setCellValue(listData.get(i).getNote());
                for (int j = 0; j <= 8; j++) {
                    row.getCell(j).setCellStyle(CommonUtils.setBorder(workbook.createCellStyle()));
                }
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Template_Export_DanhSachDonHang.xlsx.xlsx");
            workbook.write(stream);
            workbook.close();
            //Xóa file temp
            File fileToDelete = new File(Path.of(filePathTemp).toUri());
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
            //return new ResponseEntity<>(new ByteArrayResource(stream.toByteArray()), header, HttpStatus.CREATED);
        } catch (Exception e) {
            File fileToDelete = new File(Path.of(filePathTemp).toUri());
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
            throw new AppException(e);
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("redirect:/don-hang");
        }
        return new byte[0];
    }

    @Override
    public byte[] exportToCSV(Integer pOrderId, List<Integer> pOrderIds, boolean isExportAll, HttpServletResponse response) {
        return new byte[0];
    }

    @Override
    public void exportToPDF(Integer pOrderId, List<Integer> pOrderIds, boolean isExportAll, HttpServletResponse response) {
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
        parameterMap.put("barcode", Path.of(CommonUtils.rootPath + "/" + f.getDirectoryPath() + "/" + f.getStorageName()));

        // orderDetails
        List<OrderDetailRpt> listDetail = new ArrayList<>();
        for (OrderDetailDTO detailDTO : dto.getListOrderDetailDTO()) {
            OrderDetailRpt rpt = new OrderDetailRpt();
            rpt.setProductName(detailDTO.getProductVariantDTO().getVariantName());
            rpt.setUnitPrice(detailDTO.getUnitPrice());
            rpt.setQuantity(detailDTO.getQuantity());
            rpt.setSubTotal(detailDTO.getUnitPrice().multiply(BigDecimal.valueOf(detailDTO.getQuantity())));
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