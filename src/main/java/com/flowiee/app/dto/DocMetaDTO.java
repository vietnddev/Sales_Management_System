package com.flowiee.app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DocMetaDTO {
    private int docDataId;
    private String docDataValue;
    private String docFieldName;
    private String docFieldTypeInput;
    private boolean docFieldRequired;
	@Override
	public String toString() {
		return "DocMetaDTO [docDataId=" + docDataId + ", docDataValue=" + docDataValue + ", docFieldName="
				+ docFieldName + ", docFieldTypeInput=" + docFieldTypeInput + ", docFieldRequired=" + docFieldRequired
				+ "]";
	}
}