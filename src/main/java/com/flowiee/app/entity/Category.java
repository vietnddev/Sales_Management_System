package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import com.flowiee.app.utils.FlowieeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "type", length = 20, nullable = false)
	private String type;

	@Column(name = "code", length = 20)
	private String code;

	@Column(name = "name", length = 50, nullable = false)
	private String name;

	@Column(name = "sort")
	private Integer sort;

	@Column(name = "color")
	private String color;

	@Column(name = "note", length = 255)
	private String note;

	@Column(name = "endpoint", length = 50)
	private String endpoint;

	@Column(name = "is_default", length = 1, nullable = false)
	private String isDefault;

	@Column(name = "status", length = 20, nullable = false)
	private Boolean status;
	
	@OneToMany(mappedBy = "paymentMethod", fetch = FetchType.LAZY)
	private List<TicketImportGoods> listPaymentMethod;
	
	@OneToMany(mappedBy = "kenhBanHang", fetch = FetchType.LAZY)
	private List<Order> listKenhBanHang;
	
	@OneToMany(mappedBy = "fabricType", fetch = FetchType.LAZY)
	private List<ProductVariant> listFabricType;
	
	@OneToMany(mappedBy = "color", fetch = FetchType.LAZY)
	private List<ProductVariant> listLoaiMauSac;
	
	@OneToMany(mappedBy = "size", fetch = FetchType.LAZY)
	private List<ProductVariant> listLoaiKichCo;
	
	@OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
	private List<Material> listUnit;
	
	@OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
	private List<MaterialTemp> listUnitTemp;
	
	@OneToMany(mappedBy = "hinhThucThanhToan", fetch = FetchType.LAZY)
	private List<OrderPay> listPayMethod;

	@OneToMany(mappedBy = "trangThaiDonHang", fetch = FetchType.LAZY)
	private List<Order> listTrangThaiDonHang;

	@OneToMany(mappedBy = "loaiTaiLieu", fetch = FetchType.LAZY)
	private List<Document> listDocument;

	@OneToMany(mappedBy = "loaiTaiLieu", fetch = FetchType.LAZY)
	private List<DocField> listDocfield;

	@OneToMany(mappedBy = "productType", fetch = FetchType.LAZY)
	private List<Product> listProductByProductType;

	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	private List<Product> listProductByBrand;

	@OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
	private List<Product> listProductByUnit;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<CategoryHistory> listCategoryHistory;

	public Category(Integer id, String name) {
		super.id = id;
		this.name = name;
	}

	public Map<String, String> compareTo(Category categoryToCompare) {
		Map<String, String> map = new HashMap<>();
		if (!this.getCode().equals(categoryToCompare.getCode())) {
			map.put("Code", this.getCode() + "#" + categoryToCompare.getCode());
		}
		if (!this.getName().equals(categoryToCompare.getName())) {
			map.put("Name", this.getName() + "#" + categoryToCompare.getName());
		}
		if (!this.getColor().equals(categoryToCompare.getColor())) {
			map.put("Color label", this.getColor() + "#" + categoryToCompare.getColor());
		}
		if (!this.getNote().equals(categoryToCompare.getNote())) {
			map.put("Note", this.getNote() + "#" + categoryToCompare.getNote());
		}
		if ("N".equals(this.getIsDefault()) && "Y".equals(categoryToCompare.getIsDefault())) {
			map.put("Use default","#" + FlowieeUtil.now("HH:mm:ss dd/MM/yyyy"));
		}
		return map;
	}
}