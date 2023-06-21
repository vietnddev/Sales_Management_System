package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.danhmuc.entity.HinhThucThanhToan;
import com.flowiee.app.hethong.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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

	@CreatedDate
	@Column(name = "created_at", nullable = false ,columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",  updatable = false)
	private Date createdAt;

	@PreUpdate
	@PrePersist
	public void updateTimeStamps() {
		if (createdAt == null) {
			createdAt = new Date();
		}
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false, updatable = false)
	private Account createdBy;

	@JsonIgnoreProperties("khachHang")
	@OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
	private List<DonHang> listDonHang;
}