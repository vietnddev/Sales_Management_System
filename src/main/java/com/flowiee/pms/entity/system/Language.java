package com.flowiee.pms.entity.system;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.flowiee.pms.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "languages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Language extends BaseEntity implements Serializable {
    @Serial
	private static final long serialVersionUID = 1L;

    @Column(name = "module")
    private String module;

    @Column(name = "screen")
    private String screen;

	@Column(name = "code", nullable = false)
    private String code;
    
    @Column(name = "key", nullable = false)
    private String key;
    
    @Column(name = "value", nullable = false)
    private String value;
}