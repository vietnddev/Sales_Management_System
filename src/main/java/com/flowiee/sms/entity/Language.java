package com.flowiee.sms.entity;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.flowiee.sms.core.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sys_languages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Language extends BaseEntity implements Serializable {
    @Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "code")
    private String code;
    
    @Column(name = "key")
    private String key;
    
    @Column(name = "value")
    private String value;
}