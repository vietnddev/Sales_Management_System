package com.flowiee.app.model.storage;

import lombok.Data;

@Data
public class DocMetaResponse {
    private int docDataId;
    private String docDataValue;
    private String docFieldName;
    private String docFieldTypeInput;
    private boolean docFieldRequired;
}