package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;
import javax.persistence.*;

@Builder
@Entity
@Table(name = "flowiee_log")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SystemLog extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "module", length = 50, nullable = false)
	private String module;

	@JsonIgnoreProperties("listLog")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_wr", nullable = false)
	private Account account;

	@Column(name = "action",nullable = false)
	private String action;

	@Column(name = "noi_dung", length = 2000, nullable = false)
	private String noiDung;

	@Column(name = "noi_dung_cap_nhat", length = 2000)
	private String noiDungCapNhat;

	@Column(name = "ip", length = 20)
	private String ip;

	@Override
	public String toString() {
		return "SystemLog{" +
				"id=" + id +
				", module='" + module + '\'' +
				", account=" + account +
				", action='" + action + '\'' +
				", noiDung='" + noiDung + '\'' +
				", noiDungCapNhat='" + noiDungCapNhat + '\'' +
				", ip='" + ip + '\'' +
				'}';
	}
}