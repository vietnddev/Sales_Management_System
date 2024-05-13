package com.flowiee.pms.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "group_account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class GroupAccount extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "note")
    private String note;

    public GroupAccount(Integer id, String groupCode, String groupName) {
        this.id = id;
        this.groupCode = groupCode;
        this.groupName = groupName;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "groupAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Account> listAccount;
}