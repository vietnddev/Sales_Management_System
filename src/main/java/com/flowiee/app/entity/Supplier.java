package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "pro_supplier")
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
    private List<ProductVariant> listProductVariant;

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