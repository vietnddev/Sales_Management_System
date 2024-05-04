package com.flowiee.pms.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serial;

@Entity
@Table (name = "role")
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccountRole extends BaseEntity implements java.io.Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	@Column(name = "module", nullable = false)
	@NotNull
	private String module;

	@Column(name = "action", nullable = false)
	@NotNull
	private String action;

	@Column(name = "account_id")
	private Integer accountId;

	@Column(name = "group_id")
	private Integer groupId;

	public AccountRole(String module, String action, Integer accountId) {
		this.module = module;
		this.action = action;
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "AccountRole [module=" + module + ", action=" + action + ", accountId=" + accountId + "]";
	}
}