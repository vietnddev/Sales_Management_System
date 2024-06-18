package com.flowiee.pms.model;

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
public class EximModel {
    private LocalTime beginTime;
    private LocalTime finishTime;
    private Path pathSource;
    private Path pathTarget;
    private InputStreamResource content;
    private String fileType;
    private String defaultOutputName;
    private HttpHeaders httpHeaders;
    private String result;

    public EximModel(TemplateExport templateExport) {
        beginTime = LocalTime.now();
        long currentTime = beginTime.toNanoOfDay();
        pathSource = Path.of(FileUtils.excelTemplatePath + "/" + templateExport.getTemplateName());
        pathTarget = Path.of(FileUtils.excelTemplatePath + "/temp/" + currentTime + "_" + templateExport.getTemplateName());
        fileType = FileUtils.getFileExtension(templateExport.getTemplateName());
        defaultOutputName = currentTime + "_" + templateExport.getTemplateName();
    }
}