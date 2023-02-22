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
	@Column(name = "detailOrderID", unique = true, nullable = false)
	private int detailOrderID;
	private int ordersID;
	private int idProduct;
	private String name;
	private Double unitPrice;
	private int quantity;
	private Double totalMoney;
	private String note;
	private boolean status;
}
