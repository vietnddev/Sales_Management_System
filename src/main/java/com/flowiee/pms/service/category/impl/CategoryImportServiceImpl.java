package com.flowiee.pms.service.category.impl;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.repository.category.CategoryRepository;
import com.flowiee.pms.service.BaseImportService;
import com.flowiee.pms.utils.CommonUtils;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryImportServiceImpl extends BaseImportService {
    private final CategoryRepository mvCategoryRepository;

    public CategoryImportServiceImpl(CategoryRepository categoryRepository) {
        this.mvCategoryRepository = categoryRepository;
    }

    @Override
    protected void writeData() {
        List<Category> lvListToImport = new ArrayList<>();
        try {
            XSSFSheet sheet = mvWorkbook.getSheetAt(0);
            for (int i = 3; i < sheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheet.getRow(i);
                if (row != null) {
                    String categoryCode = row.getCell(1).getStringCellValue();
                    String categoryName = row.getCell(2).getStringCellValue();
                    String categoryNote = row.getCell(3).getStringCellValue();
                    if (categoryName == null || categoryName.isEmpty()) {
                        XSSFCellStyle cellStyle = mvWorkbook.createCellStyle();
                        XSSFFont fontStyle = mvWorkbook.createFont();
                        row.getCell(1).setCellStyle(CommonUtils.highlightDataImportError(cellStyle, fontStyle));
                        row.getCell(2).setCellStyle(CommonUtils.highlightDataImportError(cellStyle, fontStyle));
                        row.getCell(3).setCellStyle(CommonUtils.highlightDataImportError(cellStyle, fontStyle));
                        continue;
                    }

                    Category category = new Category();
                    category.setType(null);
                    category.setCode(!categoryCode.isEmpty() ? categoryCode : CommonUtils.genCategoryCodeByName(categoryName));
                    category.setName(categoryName);
                    category.setNote(categoryNote);

                    lvListToImport.add(category);
                }
            }
            List<Category> listCategorySaved = mvCategoryRepository.saveAll(lvListToImport);
            mvFileImportHistory.setTotalRecord(listCategorySaved.size());
            mvFileImportHistory.setResult("OK");
        } catch (Exception ex) {
            mvFileImportHistory.setResult("NOK");
        }
    }
}