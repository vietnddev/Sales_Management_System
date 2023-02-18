package com.flowiee.app.model.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "DocType")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class STGDocType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "docTypeID", unique = true, nullable = false)
	private int docTypeID;
	private String name;
	private String describes;
	private int fileCount;
	private int sizeSum;
	private int sort;
	private boolean status;
}
