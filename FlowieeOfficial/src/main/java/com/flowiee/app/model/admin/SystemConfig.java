package com.flowiee.app.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "SystemConfig")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SystemConfig implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int ID;
	private String code;
	private String name;
	private String address;
	private String email;
	private String phone;
	private String logo;
	private String favicon;
	private int loginLock;
	private String describes;
}
