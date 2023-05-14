package com.flowiee.app.role.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class AccountRoleEntity implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "module", nullable = false)
	@NotNull
	private String module;

	@Column(name = "action", nullable = false)
	@NotNull
	private String action;

	@Column(name = "account_id", nullable = false)
	@NotNull
	private int accountId;
}