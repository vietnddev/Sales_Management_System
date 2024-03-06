package com.flowiee.sms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.sms.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
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
public class Category extends BaseEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(name = "type", length = 20, nullable = false)
	private String type;

	@Column(name = "code", length = 20, columnDefinition = "VARCHAR2(20) DEFAULT ''")
	private String code;

	@Column(name = "name", length = 50, nullable = false)
	private String name;

	@Column(name = "sort")
	private Integer sort;

	@Column(name = "icon")
	private String icon;

	@Column(name = "color")
	private String color;

	@Column(name = "parent_id")
	private Integer parentId;

	@Column(name = "note", length = 255)
	private String note;

	@Column(name = "endpoint", length = 50)
	private String endpoint;

	@Column(name = "is_default", length = 1, nullable = false)
	private String isDefault;

	@Column(name = "status", length = 20, nullable = false)
	private Boolean status;

	@Transient
	private Integer totalSubRecords;

	@JsonIgnore
	@OneToMany(mappedBy = "paymentMethod", fetch = FetchType.LAZY)
	private List<TicketImport> listPaymentMethod;

	@JsonIgnore
	@OneToMany(mappedBy = "kenhBanHang", fetch = FetchType.LAZY)
	private List<Order> listKenhBanHang;

	@JsonIgnore
	@OneToMany(mappedBy = "fabricType", fetch = FetchType.LAZY)
	private List<ProductDetail> listFabricType;

	@JsonIgnore
	@OneToMany(mappedBy = "color", fetch = FetchType.LAZY)
	private List<ProductDetail> listLoaiMauSac;

	@JsonIgnore
	@OneToMany(mappedBy = "size", fetch = FetchType.LAZY)
	private List<ProductDetail> listLoaiKichCo;

	@JsonIgnore
	@OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
	private List<Material> listUnit;

	@JsonIgnore
	@OneToMany(mappedBy = "paymentMethod", fetch = FetchType.LAZY)
	private List<Order> listOrderPayment;

	@JsonIgnore
	@OneToMany(mappedBy = "trangThaiDonHang", fetch = FetchType.LAZY)
	private List<Order> listTrangThaiDonHang;

	@JsonIgnore
	@OneToMany(mappedBy = "productType", fetch = FetchType.LAZY)
	private List<Product> listProductByProductType;

	@JsonIgnore
	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	private List<Product> listProductByBrand;

	@JsonIgnore
	@OneToMany(mappedBy = "unit", fetch = FetchType.LAZY)
	private List<Product> listProductByUnit;

	@JsonIgnore
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<CategoryHistory> listCategoryHistory;

	public Category(Integer id, String name) {
		super.id = id;
		this.name = name;
	}

	public Map<String, String> compareTo(Category categoryToCompare) {
		Map<String, String> map = new HashMap<>();
		if (!this.getCode().equals(categoryToCompare.getCode())) {
			map.put("CODE", this.getCode() + "#" + categoryToCompare.getCode());
		}
		if (!this.getName().equals(categoryToCompare.getName())) {
			map.put("NAME", this.getName() + "#" + categoryToCompare.getName());
		}
		return map;
	}

	@Override
	public String toString() {
		return "Category [id= " + super.id + ", type=" + type + ", code=" + code + ", name=" + name + ", sort=" + sort + ", color=" + color
				+ ", note=" + note + ", endpoint=" + endpoint + ", isDefault=" + isDefault + ", status=" + status + "]";
	}
}