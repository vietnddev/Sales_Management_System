package com.flowiee.pms.controller;

import com.flowiee.pms.model.ExportDataModel;
import com.flowiee.pms.service.ExportService;
import com.flowiee.pms.utils.constants.TemplateExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Qualifier("ledgerExportServiceImpl")
    @Autowired
    ExportService exportService;

    @RequestMapping("/test")
    public ResponseEntity<InputStreamResource> testExport() {
        ExportDataModel model = exportService.exportToExcel(TemplateExport.LEDGER_TRANSACTIONS, null, false);
        return ResponseEntity.ok().headers(model.getHttpHeaders()).body(model.getContent());
    }
}