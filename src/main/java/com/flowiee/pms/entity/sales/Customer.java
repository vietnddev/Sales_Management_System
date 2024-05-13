package com.flowiee.pms.entity.sales;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.BaseEntity;
import com.flowiee.pms.model.dto.CustomerDTO;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@Entity
@Table(name = "customer")
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

	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "birthday")
	private LocalDate dateOfBirth;

	@Column(name = "sex", nullable = false)
	private boolean sex;

	@Column(name = "marital_status")
	private String maritalStatus;

	@Column(name = "referral_source")
	private String referralSource;

	@Column(name = "black_list")
	private String isBlackList;

	@Column(name = "black_list_reason")
	private String blackListReason;

	@Column(name = "loyalty_points")
	private Integer loyaltyPoints;

	@Column(name = "has_outstanding_balance")
	private Boolean hasOutstandingBalance;

	@Column(name = "outstanding_balance_amount")
	private BigDecimal outstandingBalanceAmount;

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
		customer.setCustomerName(dto.getCustomerName());
		customer.setDateOfBirth(dto.getDateOfBirth());
		customer.setSex(dto.isSex());
		return customer;
	}

	@Override
	public String toString() {
		return "Customer [id=" + super.id + ", customerName=" + customerName + ", birthday=" + dateOfBirth + ", sex=" + sex  + "]";
	}
}