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
@Table(name = "DocField")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class STGDocField {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "docFieldID", unique = true, nullable = false)
	private int docFieldID;
	@Column(name = "IDDocType")
	private int idDocType;
	private String type;
	private String name;
	private boolean required;
	private int sort;
}
