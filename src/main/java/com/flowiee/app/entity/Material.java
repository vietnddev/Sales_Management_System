package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "pro_material")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Material extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_import_id")
    private TicketImportGoods ticketImportGoods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private String quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit", nullable = false)
    private Category unit;

    @Column(name = "location")
    private String location;

    @Column(name = "note")
    private String note;

    @Column(name = "status", nullable = false)
    private boolean status;

    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY)
    private List<MaterialHistory> listMaterialHistory;

    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY)
    private List<Price> listPrice;

    public Material(int id) {
        super.id = id;
    }

    public Map<String, String> compareTo(Material materialToCompare) {
        Map<String, String> map = new HashMap<>();
        if (!this.getSupplier().getName().equals(materialToCompare.getSupplier().getName())) {
            map.put("Supplier name", this.getSupplier().getName() + "#" + materialToCompare.getSupplier().getName());
        }
        if (!this.getCode().equals(materialToCompare.getCode())) {
            map.put("Code", this.getCode() + "#" + materialToCompare.getCode());
        }
        if (!this.getName().equals(materialToCompare.getName())) {
            map.put("Name", this.getName() + "#" + materialToCompare.getName());
        }
        if (!this.getQuantity().equals(materialToCompare.getQuantity())) {
            map.put("Quantity", this.getQuantity() + "#" + materialToCompare.getQuantity());
        }
        if (!this.getUnit().getName().equals(materialToCompare.getUnit().getName())) {
            map.put("Unit", this.getUnit().getName() + "#" + materialToCompare.getUnit().getName());
        }
        if (!this.getLocation().equals(materialToCompare.getLocation())) {
            map.put("Location", this.getLocation() + "#" + materialToCompare.getLocation());
        }
        if (!this.getNote().equals(materialToCompare.getNote())) {
            map.put("Description", this.getNote() + "#" + materialToCompare.getNote());
        }
        if (!this.isStatus() && materialToCompare.isStatus()) {
            map.put("Status", "Disable#Enable");
        }
        if (this.isStatus() && !materialToCompare.isStatus()) {
            map.put("Status", "Enable#Disable");
        }
        return map;
    }

	@Override
	public String toString() {
		return "Material [id=" + super.id + ", ticketImportGoods=" + ticketImportGoods + ", supplier=" + supplier + ", code=" + code
				+ ", name=" + name + ", quantity=" + quantity + ", unit=" + unit + ", location=" + location + ", note="
				+ note + ", status=" + status + "]";
	}        
}