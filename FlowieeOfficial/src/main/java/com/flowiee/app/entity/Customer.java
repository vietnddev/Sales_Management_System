package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@Table(name = "khach_hang")
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

	@JsonIgnoreProperties("customer")
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<Order> listOrder;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<CustomerContact> listCustomerContact;

	public Customer(int id) {
		super.id = id;
	}

	@Override
	public String toString() {
		return "KhachHang{" +
				"id=" + id +
				", tenKhachHang='" + tenKhachHang + '\'' +
				'}';
	}
}