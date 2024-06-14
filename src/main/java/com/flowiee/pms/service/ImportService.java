package com.flowiee.pms.service;

import com.flowiee.pms.model.ExportDataModel;
import com.flowiee.pms.utils.constants.TemplateExport;
import org.springframework.web.multipart.MultipartFile;

public interface ImportService {
    ExportDataModel importFromExcel(TemplateExport templateExport, MultipartFile multipartFile);
}