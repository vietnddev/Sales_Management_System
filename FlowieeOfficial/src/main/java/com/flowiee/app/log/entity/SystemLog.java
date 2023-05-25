package com.flowiee.app.log.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.log.model.SystemLogAction;
import com.flowiee.app.nguoidung.entity.TaiKhoan;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "nhat_ky_he_thong")
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SystemLog implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "module", length = 50, nullable = false)
	private String module;

	@JsonIgnoreProperties("listLog")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false)
	private TaiKhoan taiKhoan;

	@Column(name = "action",nullable = false)
	private String action;

	@Column(name = "noi_dung", length = 2000, nullable = false)
	private String noiDung;

	@Column(name = "noi_dung_cap_nhat", length = 2000)
	private String noiDungCapNhat;

	@Column(name = "ip", length = 20)
	private String ip;

	@CreatedDate
	@Column(name = "created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",  updatable = false)
	private Date createdAt;

	@PreUpdate
	@PrePersist
	public void updateTimeStamps() {
		if (createdAt == null) {
			createdAt = new Date();
		}
	}
}