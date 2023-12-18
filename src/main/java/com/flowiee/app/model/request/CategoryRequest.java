package com.flowiee.app.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {
	private Integer id;
	private String type;
	private String code;
	private String name;
	private Integer sort;
	private String isDefault;
	private Boolean status;
}