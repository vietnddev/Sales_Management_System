package com.flowiee.pms.entity.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@Column(name = "function", nullable = false)
	private String function;

	@Column(name = "title")
	private String title;

	@Column(name = "object")
	private String object;

	@Column(name = "action_mode", nullable = false)
	private String mode;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "content", length = 4000, nullable = false, columnDefinition = "CLOB")
	private String content;

	@Column(name = "content_change", length = 4000)
	private String contentChange;

	@Column(name = "ip", length = 20)
	private String ip;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@Transient
	private String username;

	@Transient
	private String accountName;

	@PreUpdate
	public void updateAudit() {
		if (ip == null) {
			ip = CommonUtils.getUserPrincipal().getIp();
		} else {
			ip = "unknown";
		}
	}

	@Override
	public String toString() {
		return "SystemLog [id=" + super.id + ", module=" + module + ", action=" + function + ", content=" + content + ", contentChange=" + contentChange + ", ip=" + ip + ", username=" + username + "]";
	}
}