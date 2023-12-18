package com.flowiee.app.model.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
	private Integer id;
	private String name;
	private Date birthday;
	private Boolean gender;
}