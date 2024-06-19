package com.flowiee.pms.service.sales.impl;

import com.flowiee.pms.entity.sales.LedgerTransaction;
import com.flowiee.pms.model.GeneralLedger;
import com.flowiee.pms.service.BaseExportService;
import com.flowiee.pms.service.sales.LedgerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LedgerExportServiceImpl extends BaseExportService {
    LedgerService ledgerService;

    @Override
    protected void writeData(Object pCondition) {
        GeneralLedger lvData = ledgerService.findGeneralLedger(-1, -1, null, null);
        XSSFSheet lvSheet = mvWorkbook.getSheetAt(0);
        for (int i = 0; i < lvData.getListTransactions().size(); i++) {
            LedgerTransaction t = lvData.getListTransactions().get(i);
            XSSFRow row = lvSheet.createRow(i + 3);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(t.getTranCode());
            row.createCell(2).setCellValue(t.getDescription());

            setBorderCell(row, 0, 2);
        }
    }
}