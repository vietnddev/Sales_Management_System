package com.flowiee.app.model;

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
@Entity(name = "systemlog")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SystemLog implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LogID", unique = true, nullable = false)
	private int logID;
	private String module;
	private String createdBy;
	private String action;
	private String ip;

	@CreatedDate
	@Column(name = "created", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",  updatable = false)
	private Date created;
	@PreUpdate
	@PrePersist
	public void updateTimeStamps() {
		if (created == null) {
			created = new Date();
		}
	}

	public SystemLog(String module, String createdBy, String action, String ip) {
		this.module = module;
		this.createdBy = createdBy;
		this.action = action;
		this.ip = ip;
	}
}
