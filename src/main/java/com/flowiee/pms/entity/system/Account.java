package com.flowiee.pms.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.BaseEntity;

import com.flowiee.pms.entity.sales.Customer;
import com.flowiee.pms.entity.sales.Order;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends BaseEntity implements Serializable {
	@Serial
	static final long serialVersionUID = 1L;

	@Column(name = "username", nullable = false)
	String username;

	@JsonIgnore
	@Column(name = "password", nullable = false)
	String password;

	@Column(name = "fullname", nullable = false)
	String fullName;

	@Column(name = "sex", nullable = false)
	boolean sex;

	@Column(name = "phone_number")
	String phoneNumber;

	@Column(name = "email")
	String email;

	@Column(name = "address")
	String address;

	@Column(name = "avatar")
	String avatar;

	@Column(name = "remark")
	String remark;

	@Column(name = "role")
	String role;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_account")
	GroupAccount groupAccount;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branch_id", nullable = false)
	Branch branch;

	@Column(name = "status")
	boolean status;

	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	List<FileStorage> listFileStorage;

	@JsonIgnore
	@OneToMany(mappedBy = "nhanVienBanHang", fetch = FetchType.LAZY)
	List<Order> listOrder;

	@JsonIgnore
	@OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
	List<Customer> listCustomer;

	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	List<FileImportHistory> listHistoryImportData;

	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	List<SystemLog> listLog;

	@Transient
	String ip;

	public Account(Integer id) {
		super.id = id;
	}

	public Account(Integer id, String username, String fullName) {
		super.id = id;
		this.username = username;
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [username=");
		builder.append(username);
		builder.append(", fullName=");
		builder.append(fullName);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", phoneNumber=");
		builder.append(phoneNumber);
		builder.append(", email=");
		builder.append(email);
		builder.append(", address=");
		builder.append(address);
		builder.append(", avatar=");
		builder.append(avatar);
		builder.append(", remark=");
		builder.append(remark);
		builder.append(", remark=");
		builder.append(role);
		builder.append(", status=");
		builder.append(status);
		builder.append(", ip=");
		builder.append(ip);
		builder.append("]");
		return builder.toString();
	}
}