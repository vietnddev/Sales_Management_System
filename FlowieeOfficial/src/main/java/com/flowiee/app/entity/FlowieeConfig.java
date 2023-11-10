package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table (name = "flowiee_config")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FlowieeConfig extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "key_config", nullable = false)
	private String key;

	@Column(name = "name_config", nullable = false)
	private String name;

	@Column(name = "value_config", length = 1000)
	private String value;

	@Column(name = "sort_config")
	private Integer sort;
	
	public FlowieeConfig(Integer id, String key, String name, String value, Integer sort) {
		
	}
}