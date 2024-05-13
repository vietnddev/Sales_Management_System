package com.flowiee.pms.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table (name = "config")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SystemConfig extends BaseEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "value", length = 1000)
	private String value;

	@Column(name = "sort")
	private Integer sort;

	public SystemConfig(String code, String name, String value) {
		this.code = code;
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return "SystemConfig [id=" + super.id + ", code=" + code + ", name=" + name + ", value=" + value + ", sort=" + sort + "]";
	}
}