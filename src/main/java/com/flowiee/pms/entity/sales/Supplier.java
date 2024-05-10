package com.flowiee.pms.entity.sales;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.base.BaseEntity;

import com.flowiee.pms.entity.product.Material;
import com.flowiee.pms.entity.product.ProductDetail;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "supplier")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Supplier extends BaseEntity implements Serializable {
    @Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "website")
    private String website;

    @Column(name = "contact_point")
    private String contactPoint;

    @Column(name = "product_provided")
    private String productProvided;

    @Column(name = "current_debt_amount")
    private BigDecimal currentDebtAmount;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<TicketImport> listTicketImportGoods;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<ProductDetail> listProductDetail;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<Material> listMaterial;

	@Override
	public String toString() {
		return "Supplier [id=" + super.id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", address=" + address
				+ ", productProvided=" + productProvided + ", note=" + note + ", status=" + status + "]";
	}
}