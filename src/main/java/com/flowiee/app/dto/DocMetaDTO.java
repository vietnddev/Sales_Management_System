package com.flowiee.app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DocMetaDTO {
	private Integer fieldId;
	private String fieldName;
    private Integer dataId;
    private String dataValue;
    private String fieldType;
    private Boolean fieldRequired;
	private Integer docId;

	@Override
	public String toString() {
		return "DocMetaDTO [docDataId=" + dataId + ", docDataValue=" + dataValue + ", docFieldName="
				+ fieldName + ", docFieldTypeInput=" + fieldType + ", docFieldRequired=" + fieldRequired
				+ "]";
	}
}