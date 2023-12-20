package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.model.request.CustomerRequest;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@Table(name = "pro_customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ten_khach_hang", length = 100, nullable = false)
	private String tenKhachHang;

	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "gioi_tinh", nullable = false)
	private boolean gioiTinh;

	@Transient
	private String phoneDefault;

	@Transient
	private String emailDefault;

	@Transient
	private String addressDefault;

	@JsonIgnoreProperties("customer")
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<Order> listOrder;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<CustomerContact> listCustomerContact;

	public Customer(int id) {
		super.id = id;
	}

	public Customer(int id, String customerName) {
		super.id = id;
		this.tenKhachHang = customerName;
	}
	
	public Customer fromCustomerRequest(CustomerRequest request) {
		Customer cus = new Customer();
		cus.setId(request.getId());
		cus.setTenKhachHang(request.getName());
		cus.setBirthday(request.getBirthday());
		cus.setGioiTinh(request.getGender());
		return cus;
	}

	@Override
	public String toString() {
		return "Customer [id=" + super.id + ", tenKhachHang=" + tenKhachHang + ", birthday=" + birthday + ", gioiTinh=" + gioiTinh
				+ ", phoneDefault=" + phoneDefault + ", emailDefault=" + emailDefault + ", addressDefault="
				+ addressDefault + "]";
	}
}