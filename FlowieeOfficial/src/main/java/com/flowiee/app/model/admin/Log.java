package com.flowiee.app.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Log")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Log implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LogID", unique = true, nullable = false)
	private int logID;
	private String users;
	private String type;
	private String content;
	private String url;
	private String created;
	private String ip;
	public Log(String users, String type, String content, String url, String created, String ip) {
		this.users = users;
		this.type = type;
		this.content = content;
		this.url = url;
		this.created = created;
		this.ip = ip;
	}
}
