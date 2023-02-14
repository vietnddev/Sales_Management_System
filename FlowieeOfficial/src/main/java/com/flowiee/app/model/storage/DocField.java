package com.flowiee.app.model.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DocField")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocField {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)private int ID;
	private int IDDocType;
	private String Type;
	private String Name;
	private boolean Required;
	private int Sort;
}
