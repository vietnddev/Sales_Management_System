package com.flowiee.pms.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.BaseEntity;
import com.flowiee.pms.utils.CommonUtils;
import lombok.*;
import javax.persistence.*;
import java.io.Serial;

@Builder
@Entity
@Table(name = "log")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SystemLog extends BaseEntity implements java.io.Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "module", length = 50, nullable = false)
	private String module;

	@Column(name = "action_type")
	private String actionType;

	@Column(name = "action", nullable = false)
	private String action;

	@Column(name = "content", length = 4000, nullable = false)
	private String content;

	@Column(name = "content_change", length = 4000)
	private String contentChange;

	@Column(name = "ip", length = 20)
	private String ip;

	@Transient
	private String username;

	public SystemLog (String module, String action, String value, String newValue) {
		this.module = module;
		this.action = action;
		this.content = value;
		this.contentChange = newValue;
		this.ip = "unknown";
	}

	@PreUpdate
	public void updateAudit() {
		if (ip == null && CommonUtils.mvInitData) {
			ip = CommonUtils.getUserPrincipal().getIp();
		} else {
			ip = "unknown";
		}
	}

	@Override
	public String toString() {
		return "SystemLog [id=" + super.id + ", module=" + module + ", action=" + action + ", content=" + content + ", contentChange=" + contentChange + ", ip=" + ip + ", username=" + username + "]";
	}
}