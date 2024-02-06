package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.model.request.AccountRequest;
import com.flowiee.app.model.role.Role;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Entity
@Table(name = "sys_account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Account extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "fullname", nullable = false)
	private String hoTen;

	@Column(name = "sex", nullable = false)
	private boolean gioiTinh;

	@Column(name = "phonenumber")
	private String soDienThoai;

	@Column(name = "email")
	private String email;

	@Column(name = "address")
	private String diaChi;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "remark")
	private String ghiChu;

	@Column(name = "role")
	private String role;

	@Column(name = "status")
	private boolean trangThai;

	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	private List<FileStorage> listFileStorage;

	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	private List<DocShare> listDocShare;

	@JsonIgnore
	@OneToMany(mappedBy = "nhanVienBanHang", fetch = FetchType.LAZY)
	private List<Order> listOrder;

	@JsonIgnore
	@OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
	private List<Customer> listCustomer;

	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	private List<FlowieeImport> listHistoryImportData;

	@Transient
	private List<Role> listRole;

	@Transient
	private String ip;

	public Account(Integer id) {
		super.id = id;
	}

	public Account(Integer id, String username, String hoTen) {
		super.id = id;
		this.username = username;
		this.hoTen = hoTen;
	}

	public Account fromAccountRequest(AccountRequest request) {
		Account acc = new Account();
		acc.setId(request.getId());
		acc.setUsername(request.getUsername());
		acc.setPassword(request.getPassword());
		acc.setHoTen(request.getName());
		acc.setGioiTinh(request.getSex());
		acc.setSoDienThoai(request.getPhone());
		acc.setEmail(request.getEmail());
		acc.setDiaChi(request.getAddress());
		acc.setAvatar(request.getAvatar());
		acc.setGhiChu(request.getNote());
		acc.setRole(request.getRole());
		acc.setTrangThai(request.getStatus());
		return acc;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", hoTen=");
		builder.append(hoTen);
		builder.append(", gioiTinh=");
		builder.append(gioiTinh);
		builder.append(", soDienThoai=");
		builder.append(soDienThoai);
		builder.append(", email=");
		builder.append(email);
		builder.append(", diaChi=");
		builder.append(diaChi);
		builder.append(", avatar=");
		builder.append(avatar);
		builder.append(", ghiChu=");
		builder.append(ghiChu);
		builder.append(", role=");
		builder.append(role);
		builder.append(", trangThai=");
		builder.append(trangThai);
		builder.append(", ip=");
		builder.append(ip);
		builder.append("]");
		return builder.toString();
	}
}