package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Entity
@Table(name = "pro_product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id", nullable = false)
    private Category productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Category brand;

    @Column(name = "ten_san_pham", nullable = false)
    private String tenSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Category unit;

    @Lob
    @Column(name = "mo_ta_san_pham", length = 30000, columnDefinition = "CLOB")
    private String moTaSanPham;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductVariant> listBienThe;

    @JsonIgnoreProperties("product")
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<FileStorage> listFileStorage;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductHistory> listProductHistory;

    @Transient
    private FileStorage imageActive;

    public Product(int id) {
        super.id = id;
    }

    public Product(Integer id, String name) {
        super.id = id;
        this.tenSanPham = name;
    }

    public Map<String, String> compareTo(Product productToCompare) {
        Map<String, String> map = new HashMap<>();
        if (!this.productType.getName().equals(productToCompare.getProductType().getName())) {
            map.put("Product type", this.productType.getName() + "#" + productToCompare.getProductType().getName());
        }
        if (!this.brand.getName().equals(productToCompare.getBrand().getName())) {
            map.put("Brand name", this.brand.getName() + "#" + productToCompare.getBrand().getName());
        }
        if (!this.unit.getName().equals(productToCompare.getUnit().getName())) {
            map.put("Unit name", this.unit.getName() + "#" + productToCompare.getUnit().getName());
        }
        if (!this.tenSanPham.equals(productToCompare.getTenSanPham())) {
            map.put("Product name", this.tenSanPham + "#" + productToCompare.getTenSanPham());
        }
        if (this.getMoTaSanPham() == null) this.setMoTaSanPham("-");      
        if (!this.moTaSanPham.equals(productToCompare.getMoTaSanPham())) {
            String descriptionOld = this.moTaSanPham.length() > 9999 ? this.moTaSanPham.substring(0, 9999) : this.moTaSanPham;
            String descriptionNew = productToCompare.getMoTaSanPham().length() > 9999 ? productToCompare.getMoTaSanPham().substring(0, 9999) : productToCompare.getMoTaSanPham();
            map.put("Product description", descriptionOld + "#" + descriptionNew);
        }
        if (!this.status.equals(productToCompare.getStatus())) {
            map.put("Product status", this.status + "#" + productToCompare.getStatus());
        }
        if ((this.imageActive != null && productToCompare.getImageActive() != null) && !this.imageActive.getId().equals(productToCompare.getImageActive().getId())) {
            map.put("Image active", this.imageActive.getId() + "#" + productToCompare.getId());
        }
        if ((this.listBienThe != null && productToCompare.getListBienThe() != null) && this.listBienThe.size() < productToCompare.getListBienThe().size()) {
            map.put("Insert new product variant", "#");
        }
        return map;
    }

	@Override
	public String toString() {
		return "Product [id=" + super.id + ", productType=" + productType + ", brand=" + brand + ", tenSanPham=" + tenSanPham + ", unit="
				+ unit + ", moTaSanPham=" + moTaSanPham + ", status=" + status + ", imageActive=" + imageActive + "]";
	}
}