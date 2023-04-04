package com.flowiee.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import com.flowiee.app.model.*;

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
