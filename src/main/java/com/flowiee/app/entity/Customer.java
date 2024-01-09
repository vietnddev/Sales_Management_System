package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.dto.CustomerDTO;
import com.flowiee.app.model.request.CustomerRequest;

import com.flowiee.app.utils.CommonUtil;
import com.flowiee.app.utils.DateUtils;
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

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<CustomerContact> listCustomerContact;

	public Customer(int id) {
		super.id = id;
	}

	public Customer(int id, String customerName) {
		super.id = id;
		this.tenKhachHang = customerName;
	}
	
	public static Customer fromCustomerRequest(CustomerRequest request) {
		Customer cus = new Customer();
		cus.setId(request.getId());
		cus.setTenKhachHang(request.getName());
		cus.setBirthday(request.getBirthday());
		cus.setGioiTinh(request.getGender());
		return cus;
	}

	public static Customer fromCustomerDTO(CustomerDTO dto) {
		Customer customer = new Customer();
		customer.setId(dto.getId());
		customer.setTenKhachHang(dto.getName());
		customer.setBirthday(DateUtils.convertStringToDate(dto.getBirthday(), "YYYY-MM-dd"));
		if (dto.getSex() != null) {
			customer.setGioiTinh("M".equals(dto.getSex()));
		}
		customer.setPhoneDefault(dto.getPhoneDefault());
		customer.setEmailDefault(dto.getEmailDefault());
		customer.setAddressDefault(dto.getAddressDefault());
		return customer;
	}

	@Override
	public String toString() {
		return "Customer [id=" + super.id + ", tenKhachHang=" + tenKhachHang + ", birthday=" + birthday + ", gioiTinh=" + gioiTinh
				+ ", phoneDefault=" + phoneDefault + ", emailDefault=" + emailDefault + ", addressDefault="
				+ addressDefault + "]";
	}
}