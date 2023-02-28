package com.flowiee.app.model.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Storage")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Storage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "storageID", unique = true, nullable = false)
	private int storageID;
	private int idParent;
	private boolean isFolder;
	private String name;
	private String stgName;
	private String describes;
	private String extension;
	private double size;
	private String author;
	private String path;
	private int idDocType;
}
