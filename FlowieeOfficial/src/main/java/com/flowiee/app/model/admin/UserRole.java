package com.flowiee.app.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "UserRole")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserRole implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int ID;
	private int idUser;
	private int idRole;
}
