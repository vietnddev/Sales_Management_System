package com.flowiee.pms.utils.constants;

import lombok.Getter;

@Getter
public enum FileExtension {
    PNG("png"),
    JPG("jpg"),
    JPEG("jpeg"),
    PDF("pdf"),
    XLS("xls"),
    XLSX("xlsx"),
    PPT("ppt"),
    PPTX("pptx"),
    DOC("doc"),
    DOCX("docx");

    private final String label;

    FileExtension(String label) {
        this.label = label;
    }
}