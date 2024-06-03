package com.flowiee.pms.model;

import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.FileUtils;
import com.flowiee.pms.utils.constants.TemplateExport;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;

import java.nio.file.Path;
import java.time.LocalTime;

@Getter
@Setter
public class ExportDataModel {
    private LocalTime beginTime;
    private long beginTimeLong;
    private LocalTime finishTime;
    private Path pathSource;
    private Path pathTarget;
    private InputStreamResource content;
    private String fileType;
    private String defaultOutputName;
    private HttpHeaders httpHeaders;

    public ExportDataModel(TemplateExport templateExport) {
        beginTime = LocalTime.now();
        beginTimeLong = beginTime.toNanoOfDay();
        pathSource = Path.of(FileUtils.excelTemplatePath + "/" + templateExport.getTemplateName());
        pathTarget = Path.of(FileUtils.excelTemplatePath + "/temp/" + beginTimeLong + "_" + templateExport.getTemplateName());
        fileType = CommonUtils.getFileExtension(templateExport.getTemplateName());
        defaultOutputName = beginTimeLong + "_" + templateExport.getTemplateName();
    }
}