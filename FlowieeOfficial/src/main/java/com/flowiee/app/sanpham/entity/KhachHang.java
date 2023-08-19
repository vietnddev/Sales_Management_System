package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.common.entity.BaseEntity;
import com.flowiee.app.hethong.entity.Account;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

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

	@Column(name = "gioi_tinh", nullable = false)
	private boolean gioiTinh;

	@Column(name = "so_dien_thoai", length = 20, nullable = false)
	private String soDienThoai;

	@Column(name = "email", length = 50, nullable = false)
	private String email;

	@Column(name = "dia_chi", length = 500, nullable = false)
	private String diaChi;

	@JsonIgnoreProperties("khachHang")
	@OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
	private List<DonHang> listDonHang;

	public KhachHang(int id) {
		super.id = id;
	}

	@Override
	public String toString() {
		return "KhachHang{" +
				"id=" + id +
				", tenKhachHang='" + tenKhachHang + '\'' +
				", gioiTinh=" + gioiTinh +
				", soDienThoai='" + soDienThoai + '\'' +
				", email='" + email + '\'' +
				", diaChi='" + diaChi + '\'' +
				'}';
	}
}