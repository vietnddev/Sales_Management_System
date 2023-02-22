package com.flowiee.app.model.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Orders implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ordersID", unique = true, nullable = false)
	private int ordersID;
	private String code;
	private int idCustomer;
	private String name;
	private String phone;
	private String email;
	private String address;
	private String note;
	private String date;
	private Double totalMoney;
	private String sales;
	private String channel;
	private String status;
}
