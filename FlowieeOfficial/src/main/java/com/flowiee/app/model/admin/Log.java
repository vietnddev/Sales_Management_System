package com.flowiee.app.model.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Log")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Log implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int ID;
	private String users;
	private String action;
	private String url;
	private String created;
	private String ip;
}
