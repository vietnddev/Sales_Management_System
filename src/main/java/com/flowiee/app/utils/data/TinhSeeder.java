package com.flowiee.app.utils.data;

import com.flowiee.app.entity.Category;
import com.flowiee.app.service.CategoryService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;

@Component
public class TinhSeeder implements CommandLineRunner {
    @Value("${seeder.module:0}")
    private String module;

    @Autowired
    private CategoryService dmTinhService;

    @Override
    public void run(String... args) throws Exception {
        if (module.equals("tinh")) {
            seeding();
            exitProgram();
        }
    }

    private void seeding() {
        try {
            File resource = new ClassPathResource("data/excel/Danhmuc_Tinh_Data.xlsx").getFile();
            if (!resource.exists()) {
                throw new FileNotFoundException("File import does not exists!");
            }
            XSSFWorkbook workbook = new XSSFWorkbook(resource);
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (int i = 4; i < sheet.getPhysicalNumberOfRows(); i++) {
                Category model = new Category();
                model.setType("PROVINCE");
                model.setIsDefault("0");
                model.setStatus(false);
                XSSFRow row = sheet.getRow(i);

                XSSFCell maTinh = row.getCell(0);
                String tenTinh = row.getCell(1).getStringCellValue();

                switch (maTinh.getCellType()) {
                    case STRING:
                        model.setCode(maTinh.getStringCellValue());
                        break;
                    case NUMERIC:
                        model.setCode(String.valueOf((int) maTinh.getNumericCellValue()));
                        break;
                }
                model.setName(tenTinh);

                if (model.getCode().isEmpty() || model.getName().isEmpty()) {
                    continue;
                }
                dmTinhService.save(model);
                System.out.println("Import thanh cong tinh " + model.getName());
            }
        } catch (Exception e) {
            System.out.println("\n seeding throw exception:::" + e.getMessage() + "\n");
        }
    }

    private void exitProgram() {
        System.exit(0);
    }
}
