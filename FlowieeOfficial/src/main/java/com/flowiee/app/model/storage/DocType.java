package com.flowiee.app.model.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DocType")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int ID;
	private String Name;
	private String Describes;
	private int FileCount;
	private int SizeSum;
	private int Sort;
	private boolean Status;
}
