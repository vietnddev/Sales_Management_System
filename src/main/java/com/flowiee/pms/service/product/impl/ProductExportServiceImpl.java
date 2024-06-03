package com.flowiee.pms.service.product.impl;

import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.service.BaseExport;
import com.flowiee.pms.service.product.ProductVariantService;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductExportServiceImpl extends BaseExport {
    private final ProductVariantService productVariantService;

    public ProductExportServiceImpl(ProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

    @Override
    protected void writeData(Object pCondition) {
        XSSFSheet sheet = mvWorkbook.getSheetAt(0);
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
                row.getCell(j).setCellStyle(FileUtils.setBorder(mvWorkbook.createCellStyle()));
            }
        }
    }
}