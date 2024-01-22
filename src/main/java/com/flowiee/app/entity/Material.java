package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.model.request.MaterialRequest;

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
    private TicketImport ticketImport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

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

    public Material fromMaterialRequest(MaterialRequest request) {
    	Material mat = new Material();
    	mat.setId(request.getId());
    	mat.setTicketImport(new TicketImport(request.getTicketImportId()));
    	mat.setSupplier(new Supplier(request.getSupplierId(), null));
    	mat.setQuantity(request.getQuantity());
    	mat.setUnit(new Category(request.getUnitId(), null));
    	mat.setCode(request.getCode());
    	mat.setName(request.getName());
    	mat.setLocation(request.getLocation());
    	mat.setNote(request.getNote());
    	mat.setStatus(request.getStatus());
    	return mat;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Material [id=");
		builder.append(super.id);
		builder.append(", ticketImportGoods=");
		builder.append(ticketImport);
		builder.append(", supplier=");
		builder.append(supplier);
		builder.append(", code=");
		builder.append(code);
		builder.append(", name=");
		builder.append(name);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unit=");
		builder.append(unit);
		builder.append(", location=");
		builder.append(location);
		builder.append(", note=");
		builder.append(note);
		builder.append(", status=");
		builder.append(status);
		builder.append(", listMaterialHistory=");
		builder.append(listMaterialHistory);
		builder.append(", listPrice=");
		builder.append(listPrice);
		builder.append("]");
		return builder.toString();
	}   
}