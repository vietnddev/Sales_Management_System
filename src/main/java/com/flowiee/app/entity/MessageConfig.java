package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sys_message")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MessageConfig extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "message_key", nullable = false)
    private String messageKey;

    @Column(name = "message_name", nullable = false)
    private String messageName;

	@Override
	public String toString() {
		return "MessageConfig [id=" + super.id + ", messageKey=" + messageKey + ", messageName=" + messageName + "]";
	}       
}