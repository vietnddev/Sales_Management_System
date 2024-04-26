package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.service.product.ProductExportService;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.service.sales.impl.OrderExportServiceImpl;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.ExcelUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductExportServiceImpl implements ProductExportService {
    Logger logger = LoggerFactory.getLogger(OrderExportServiceImpl.class);

    @Autowired
    private ProductVariantService productVariantService;

    @Override
    public ResponseEntity<?> exportToExcel(Integer pProductId, List<Integer> pProductIds, boolean isExportAll) {
        long exportTime = System.currentTimeMillis();
        String rootPath = CommonUtils.excelTemplatePath;
        String templateName = "Template_E_Product.xlsx";
        String fileNameReturn = exportTime + "_ListOfProducts.xlsx";
        Path templateOriginal = Path.of(rootPath + "/" + templateName);
        Path templateTarget = Path.of(rootPath + "/temp/" + exportTime + "_" + templateName);
        XSSFWorkbook workbook = null;
        try {
            File templateToExport = Files.copy(templateOriginal, templateTarget, StandardCopyOption.REPLACE_EXISTING).toFile();
            workbook = new XSSFWorkbook(templateToExport);
            XSSFSheet sheet = workbook.getSheetAt(0);

            List<ProductVariantDTO> listDataVariant = productVariantService.findAll();
            for (int i = 0; i < listDataVariant.size(); i++) {
                ProductVariantDTO productVariant = listDataVariant.get(i);
                XSSFRow row = sheet.createRow(i + 3);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(productVariant.getProductTypeName());
                row.createCell(2).setCellValue(productVariant.getVariantName());
                row.createCell(3).setCellValue(productVariant.getSizeName());
                row.createCell(4).setCellValue(productVariant.getColorName());
                row.createCell(5).setCellValue(productVariant.getFabricTypeName());
                row.createCell(6).setCellValue(CommonUtils.formatToVND(productVariant.getOriginalPrice()));
                row.createCell(7).setCellValue(CommonUtils.formatToVND(productVariant.getDiscountPrice()));
                row.createCell(8).setCellValue(productVariant.getStorageQty());
                row.createCell(9).setCellValue(productVariant.getSoldQty());
                row.createCell(10).setCellValue(productVariant.getStatus());
                for (int j = 0; j <= 10; j++) {
                    row.getCell(j).setCellStyle(ExcelUtils.setBorder(workbook.createCellStyle()));
                }
            }
            return new ResponseEntity<>(ExcelUtils.build(workbook), ExcelUtils.setHeaders(fileNameReturn), HttpStatus.OK);
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
    public byte[] exportToCSV(Integer pProductId, List<Integer> pProductIds, boolean isExportAll) {
        return new byte[0];
    }

    @Override
    public byte[] exportToPDF(Integer pProductId, List<Integer> pProductIds, boolean isExportAll) {
        return new byte[0];
    }
}