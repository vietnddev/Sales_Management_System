package com.flowiee.app.model.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Storage")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Storage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Storage", unique = true, nullable = false)
	private int ID;
	private int IDParent;
	private int Type;
	private String Name;
	private String StgName;
	private String Describes;
	private String Extension;
	private double Size;
	private String Author;
	private String Path;
	private int IDDocType;
}
