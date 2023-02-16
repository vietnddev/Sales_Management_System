package com.flowiee.app.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int ID;
	private int sort;
	private int type;
	private String name;
	private String describes;
	private boolean status;
	private String code;
}
