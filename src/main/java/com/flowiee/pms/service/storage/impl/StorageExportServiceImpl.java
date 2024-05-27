package com.flowiee.pms.service.storage.impl;

import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.model.StorageItems;
import com.flowiee.pms.model.dto.StorageDTO;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.storage.StorageExportService;
import com.flowiee.pms.service.storage.StorageService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.ExcelUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class StorageExportServiceImpl extends BaseService implements StorageExportService {
    @Autowired
    private StorageService storageService;

    @Override
    public ResponseEntity<?> exportToExcel(Integer pStorageId) {
        Optional<StorageDTO> storage = storageService.findById(pStorageId);
        if (storage.isEmpty()) {
            return null;
        }
        long exportTime = System.currentTimeMillis();
        String rootPath = CommonUtils.excelTemplatePath;
        String templateName = "Template_E_Storage.xlsx";
        String fileNameReturn = exportTime + "_Storage_ListOfItems.xlsx";
        Path templateOriginal = Path.of(rootPath + "/" + templateName);
        Path templateTarget = Path.of(rootPath + "/temp/" + exportTime + "_" + templateName);
        XSSFWorkbook workbook = null;
        try {
            File templateToExport = Files.copy(templateOriginal, templateTarget, StandardCopyOption.REPLACE_EXISTING).toFile();
            workbook = new XSSFWorkbook(templateToExport);
            XSSFSheet sheet = workbook.getSheetAt(0);

            sheet.getRow(1).getCell(0).getStringCellValue().replace("{storageName}", storage.get().getName());

            List<StorageItems> listData = storageService.findStorageItems( -1, -1, pStorageId, null).getContent();
            for (int i = 0; i < listData.size(); i++) {
                StorageItems storageItems = listData.get(i);
                XSSFRow row = sheet.createRow(i + 3);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(storageItems.getItemName());
                row.createCell(2).setCellValue(storageItems.getIsProduct().equals("Y") ? "Sản phẩm" : "Nguyên vật liệu");
                row.createCell(3).setCellValue(storageItems.getItemBrand());
                row.createCell(4).setCellValue(storageItems.getItemSalesAvailableQty());
                row.createCell(5).setCellValue(storageItems.getItemStorageQty());
                row.createCell(6).setCellValue(storageItems.getLastImportTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                row.createCell(7).setCellValue(storageItems.getFirstImportTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                for (int j = 0; j <= 7; j++) {
                    row.getCell(j).setCellStyle(ExcelUtils.setBorder(workbook.createCellStyle()));
                }
            }
            return new ResponseEntity<>(ExcelUtils.build(workbook), ExcelUtils.setHeaders(fileNameReturn), HttpStatus.OK);
        } catch (IOException | InvalidFormatException ex) {
            logger.error("An error when export list of storage items!", ex);
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
}