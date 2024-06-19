package com.flowiee.pms.service.category.impl;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.service.BaseExportService;
import com.flowiee.pms.service.category.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryExportServiceImpl extends BaseExportService {
    CategoryService categoryService;

    @Override
    protected void writeData(Object pCondition) {
        XSSFSheet sheet = mvWorkbook.getSheetAt(0);
        List<Category> listData = categoryService.findAll();
        for (int i = 0; i < listData.size(); i++) {
            
            XSSFRow row = sheet.createRow(i + 3);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(listData.get(i).getCode());
            row.createCell(2).setCellValue(listData.get(i).getName());
            row.createCell(3).setCellValue(listData.get(i).getNote());

            setBorderCell(row, 0, 3);
        }
    }
}