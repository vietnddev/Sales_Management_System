package com.flowiee.app.hethong.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "cau_hinh_he_thong")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CauHinhHeThong implements java.io.Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int ID;
	private String code;

	@Column(name = "ten_cua_hang", length = 500, nullable = false)
	private String name;

	private String address;
	private String email;
	private String phone;
	private String logo;
	private String favicon;
	private int loginLock;
	private String describes;
}
