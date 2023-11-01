package com.flowiee.app.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_role_history")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccountRoleHistory extends BaseEntity implements Serializable {
    @Column(name = "role_remove", length = 500)
    private String roleRemove;

    @Column(name = "role_add", length = 500)
    private String roleAdd;
}