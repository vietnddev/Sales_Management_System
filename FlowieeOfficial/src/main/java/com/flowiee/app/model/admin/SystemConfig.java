package com.flowiee.app.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
	private String Code;
	private String Name;
	private String Address;
	private String Email;
	private String Phone;
	private String Logo;
	private String Favicon;
	private int LoginLock;
	private String Describes;
}
