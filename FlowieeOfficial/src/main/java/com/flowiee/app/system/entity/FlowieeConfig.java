package com.flowiee.app.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table (name = "flowiee_config")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FlowieeConfig implements java.io.Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "key_config", nullable = false)
	private String key;

	@Column(name = "name_config", nullable = false)
	private String name;

	@Column(name = "value_config", length = 1000)
	private String value;

	@Column(name = "sort_config")
	private Integer sort;
}