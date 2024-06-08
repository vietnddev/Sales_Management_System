package com.flowiee.pms.service.category.impl;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.service.BaseExportService;
import com.flowiee.pms.service.category.CategoryService;
import com.flowiee.pms.utils.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryExportServiceImpl extends BaseExportService {
    @Autowired
    private CategoryService categoryService;

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
            for (int j = 0; j <= 3; j++) {
                row.getCell(j).setCellStyle(FileUtils.setBorder(mvWorkbook.createCellStyle()));
            }
        }
    }
}