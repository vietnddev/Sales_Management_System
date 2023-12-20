package com.flowiee.app.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialRequest {
	private Integer id;
    private Integer ticketImportId;
    private Integer supplierId;
    private Integer quantity;
    private Integer unitId;
    private String code;
    private String name;
    private String location;
    private String note;
    private Boolean status;
}