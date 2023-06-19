package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.danhmuc.entity.HinhThucThanhToan;
import com.flowiee.app.hethong.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "khach_hang")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class KhachHang implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "mat_khau", length = 255,nullable = false)
	private String matKhau;

	@Column(name = "ten_khach_hang", length = 100, nullable = false)
	private String tenKhachHang;

	@Column(name = "so_dien_thoai", length = 20, nullable = false)
	private String soDienThoai;

	@Column(name = "email", length = 50, nullable = true)
	private String email;

	@Column(name = "dia_chi", length = 500, nullable = false)
	private String diaChi;

	@Column(name = "trang_thai", nullable = false)
	private boolean trangThai;

	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false)
	private Account createdBy;

	@JsonIgnoreProperties("khachHang")
	@OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
	private List<DonHang> listDonHang;
}