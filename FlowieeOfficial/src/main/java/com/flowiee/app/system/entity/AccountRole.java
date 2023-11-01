package com.flowiee.app.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "account_role")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccountRole extends BaseEntity implements java.io.Serializable {
	@Column(name = "module", nullable = false)
	@NotNull
	private String module;

	@Column(name = "action", nullable = false)
	@NotNull
	private String action;

	@Column(name = "account_id", nullable = false)
	@NotNull
	private Integer accountId;
}