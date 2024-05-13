package com.flowiee.pms.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.entity.BaseEntity;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.sales.Supplier;
import com.flowiee.pms.entity.system.FileStorage;
import com.flowiee.pms.model.dto.MaterialDTO;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "material")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Material extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Category brand;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Category unit;

    @Column(name = "location")
    private String location;

    @Column(name = "note")
    private String note;

    @Column(name = "status", nullable = false)
    private boolean status;

    @JsonIgnore
    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY)
    private List<MaterialHistory> listMaterialHistory;

    @JsonIgnore
    @JsonIgnoreProperties("material")
    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<FileStorage> listImages;

    @JsonIgnore
    @JsonIgnoreProperties("material")
    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<MaterialTemp> listMaterialTemp;

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

    public static Material fromMaterialDTO(MaterialDTO dto) {
    	Material material = new Material();
        material.setId(dto.getId());
        material.setCode(dto.getCode());
        material.setName(dto.getName());
        material.setLocation(dto.getLocation());
        material.setUnit(new Category(dto.getUnitId(), dto.getUnitName()));
        material.setQuantity(dto.getQuantity());
        material.setNote(dto.getNote());
        return material;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Material [id=");
		builder.append(super.id);
		//builder.append(", ticketImportGoods=");
		//builder.append(ticketImport);
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
		builder.append("]");
		return builder.toString();
	}   
}