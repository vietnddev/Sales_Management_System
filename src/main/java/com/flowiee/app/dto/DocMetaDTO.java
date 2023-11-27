package com.flowiee.app.dto;

import lombok.Data;

@Data
public class DocMetaDTO {
    private int docDataId;
    private String docDataValue;
    private String docFieldName;
    private String docFieldTypeInput;
    private boolean docFieldRequired;
}