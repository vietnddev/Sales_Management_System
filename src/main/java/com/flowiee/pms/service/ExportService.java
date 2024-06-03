package com.flowiee.pms.service;

import com.flowiee.pms.model.ExportDataModel;
import com.flowiee.pms.utils.constants.TemplateExport;

public interface ExportService {
    ExportDataModel exportToExcel(TemplateExport templateExport, Object pCondition, boolean templateOnly);

    ExportDataModel exportToCsv(TemplateExport templateExport, Object pCondition);
}