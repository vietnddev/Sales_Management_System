package com.flowiee.app.entity;

import com.flowiee.app.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "sys_notification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Notification extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "send", nullable = false)
    private Integer send;

    @Column(name = "receive", nullable = false)
    private Integer receive;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "readed", nullable = false)
    private Boolean readed;

    @Column(name = "import_id")
    private Integer importId;

	@Override
	public String toString() {
		return "Notification [id=" + super.id + ", send=" + send + ", receive=" + receive + ", type=" + type + ", title=" + title
				+ ", content=" + content + ", readed=" + readed + ", importId=" + importId + "]";
	}        
}