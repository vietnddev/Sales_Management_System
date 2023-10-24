package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "khach_hang")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class KhachHang extends BaseEntity implements Serializable {
	@Column(name = "ten_khach_hang", length = 100, nullable = false)
	private String tenKhachHang;

	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "gioi_tinh", nullable = false)
	private boolean gioiTinh;

	@JsonIgnoreProperties("khachHang")
	@OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
	private List<DonHang> listDonHang;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<CustomerContact> listCustomerContact;

	public KhachHang(int id) {
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