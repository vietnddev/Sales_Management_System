package com.flowiee.pms.service.storage.impl;

import com.flowiee.pms.entity.storage.Storage;
import com.flowiee.pms.model.StorageItems;
import com.flowiee.pms.model.dto.StorageDTO;
import com.flowiee.pms.service.BaseExportService;
import com.flowiee.pms.service.storage.StorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StorageExportServiceImpl extends BaseExportService {
    StorageService storageService;

    @Override
    protected void writeData(Object pCondition) {
        Storage lvConditon = (Storage) pCondition;

        Optional<StorageDTO> storage = storageService.findById(lvConditon.getId());
        if (storage.isEmpty()) return;

        XSSFSheet sheet = mvWorkbook.getSheetAt(0);

        sheet.getRow(1).getCell(0).getStringCellValue().replace("{storageName}", storage.get().getName());

        List<StorageItems> listData = storageService.findStorageItems( -1, -1, storage.get().getId(), null).getContent();
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

            setBorderCell(row, 0, 7);
        }
    }
}