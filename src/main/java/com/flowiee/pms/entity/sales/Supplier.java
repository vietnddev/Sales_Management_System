package com.flowiee.pms.entity.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.base.BaseEntity;

import com.flowiee.pms.entity.product.Material;
import com.flowiee.pms.entity.product.ProductDetail;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
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

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "product_provided")
    private String productProvided;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<TicketImport> listTicketImportGoods;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<ProductDetail> listProductDetail;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<Material> listMaterial;

    public Supplier(Integer id, String name) {
        super.id = id;
        this.name = name;
    }

	@Override
	public String toString() {
		return "Supplier [id=" + super.id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", address=" + address
				+ ", productProvided=" + productProvided + ", note=" + note + ", status=" + status + "]";
	}
}