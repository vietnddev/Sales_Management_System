package com.flowiee.app.model.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DetailOrder")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DetailOrder implements java.io.Serializable { // ok
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int ID;
	private int IDOrders;
	private int IDProduct;
	private String Name;
	private Double UnitPrice;
	private int Quantity;
	private Double TotalMoney;
	private String Note;
	private boolean Status;
}
