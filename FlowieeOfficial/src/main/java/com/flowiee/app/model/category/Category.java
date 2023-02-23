package com.flowiee.app.model.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Category")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categoryID", unique = true, nullable = false)
	private int categoryID;
	private String code;
	private String type;
	private String name;
	private int sort;
	private String note;
	private boolean status;
}
