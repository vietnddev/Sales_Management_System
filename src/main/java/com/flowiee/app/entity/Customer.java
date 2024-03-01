package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.model.dto.CustomerDTO;

import com.flowiee.app.utils.DateUtils;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@Entity
@Table(name = "pro_customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer extends BaseEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "customer_name", length = 100, nullable = false)
	private String customerName;

	@Column(name = "birthday")
	private Date birthday;

	@Column(name = "sex", nullable = false)
	private boolean sex;

	@Transient
	private String phoneDefault;

	@Transient
	private String emailDefault;

	@Transient
	private String addressDefault;

	@JsonIgnore
	@JsonIgnoreProperties("customer")
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	private List<Order> listOrder;

	@JsonIgnore
	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<CustomerContact> listCustomerContact;

	public Customer(int id) {
		super.id = id;
	}

	public Customer(int id, String customerName) {
		super.id = id;
		this.customerName = customerName;
	}

	public static Customer fromCustomerDTO(CustomerDTO dto) {
		Customer customer = new Customer();
		customer.setId(dto.getId());
		customer.setCustomerName(dto.getName());
		customer.setBirthday(DateUtils.convertStringToDate(dto.getBirthday(), "YYYY-MM-dd"));
		if (dto.getSex() != null) {
			customer.setSex("M".equals(dto.getSex()));
		}
		customer.setPhoneDefault(dto.getPhoneDefault());
		customer.setEmailDefault(dto.getEmailDefault());
		customer.setAddressDefault(dto.getAddressDefault());
		return customer;
	}

	@Override
	public String toString() {
		return "Customer [id=" + super.id + ", customerName=" + customerName + ", birthday=" + birthday + ", sex=" + sex
				+ ", phoneDefault=" + phoneDefault + ", emailDefault=" + emailDefault + ", addressDefault="
				+ addressDefault + "]";
	}
}