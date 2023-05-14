package com.flowiee.app.danhmuc.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "danh_muc")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DanhMuc implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "ma_danh_muc", length = 50, nullable = true)
	private String maDanhMuc;

	@Column(name = "loai_danh_muc", length = 50, nullable = false)
	private String loaiDanhMuc;

	@Column(name = "ten_danh_muc", length = 50, nullable = false)
	private String tenDanhMuc;

	@Column(name = "thu_tu_hien_thi", length = 50, nullable = true)
	private int thuTuHienThi;

	@Column(name = "ghi_chu", length = 50, nullable = true)
	private String ghiChu;

	@Column(name = "trang_thai", length = 50, nullable = false)
	private boolean trangThai;
}
