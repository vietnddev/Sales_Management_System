package com.flowiee.app.log.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "nhat_ky_he_thong")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SystemLog implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "module", length = 50, nullable = false)
	private String module;

	@Column(name = "created_by", length = 50, nullable = false)
	private String createdBy;

	@Column(name = "action", length = 2000, nullable = false)
	private String action;

	@Column(name = "ip", length = 20, nullable = true)
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

	public SystemLog(String module, String createdBy, String action, String ip) {
		this.module = module;
		this.createdBy = createdBy;
		this.action = action;
		this.ip = ip;
	}
}