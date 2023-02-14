package com.flowiee.app.model.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int ID;
	private String Code;
	private int IDBrand;
	private String Type;
	private String Name;
	private String Color;
	private Double Price;
	private String Size;
	private String Date;
	private int Storage;
	private int Quantity;
	private boolean HighLight;
	private String Describes;
	private boolean Status;
	private int Promotion;
}
