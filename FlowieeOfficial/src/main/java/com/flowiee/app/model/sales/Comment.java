package com.flowiee.app.model.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Comment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int ID;
	private int IDProduct;
	private String Name;
	private String Email;
	private String Phone;
	private String Created;
	private String Describes;
	private boolean Status;
	private int Code;
}
