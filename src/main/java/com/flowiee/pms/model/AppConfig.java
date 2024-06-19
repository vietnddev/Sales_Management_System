package com.flowiee.pms.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppConfig {
    String rootPath;
    String fileUploadPath;//     = rootPath + "/uploads/";
    String initCsvDataPath;//    = rootPath + "/data/csv";
    String reportTemplatePath;// = rootPath + "/report";
    String excelTemplatePath;//  = rootPath + "/templates/excel";
    String logoPath;//           = Paths.get(FileUtils.rootPath + "/dist/img/FlowieeLogo.png");
}