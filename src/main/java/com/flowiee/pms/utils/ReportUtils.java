package com.flowiee.pms.utils;

import java.io.File;
import java.nio.file.Path;

public class ReportUtils {
    public static File getReportTemplate(String reportName) {
        return new File(Path.of(CommonUtils.reportTemplatePath + "/" + reportName + ".jasper").toUri());
    }
}