package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sys_account_role_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccountRoleHistory extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Column(name = "old_role", length = 500)
    private String oldRole;

    @Column(name = "new_role", length = 500)
    private String newRole;

	@Override
	public String toString() {
		return "AccountRoleHistory [id=" + super.id + ", oldRole=" + oldRole + ", newRole=" + newRole + "]";
	}        
}