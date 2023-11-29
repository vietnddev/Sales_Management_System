package com.flowiee.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.flowiee.app.base.BaseEntity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Entity
@Table(name = "pro_product_variant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductVariant extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(name = "variant_code", length = 50, nullable = false)
    private String maSanPham;
    
    @Column(name = "variant_name")
    private String tenBienThe;

    @Column(name = "quantity_stg", nullable = false)
    private int soLuongKho;

    @Column(name = "quantity_sell", nullable = false)
    private int soLuongDaBan;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", nullable = false)
    private Category color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", nullable = false)
    private Category size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabric_id", nullable = false)
    private Category fabricType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garment_factory_id")
    private GarmentFactory garmentFactory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_import_id")
    private TicketImportGoods ticketImportGoods;

    @Transient
    private Price price;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<ProductAttribute> listThuocTinh;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<Price> listGiaBan;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<OrderDetail> listOrderDetail;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<FileStorage> listFileStorage;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<Items> listItems;

    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY)
    private List<ProductHistory> listProductHistory;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("ProductVariant {");
        builder.append("id=").append(id).append(", ");
        builder.append("name=").append(tenBienThe);
        builder.append("}");
        return builder.toString();
    }

    public Map<String, String> compareTo(ProductVariant entityToCompare) {
        Map<String, String> map = new HashMap<>();
        if (!this.getMaSanPham().equals(entityToCompare.getMaSanPham())) {
            map.put("Product code", this.getMaSanPham() + "#" + entityToCompare.getMaSanPham());
        }
        if (!this.getTenBienThe().equals(entityToCompare.getTenBienThe())) {
            map.put("Product name", this.getTenBienThe() + "#" + entityToCompare.getTenBienThe());
        }
        if (!this.getColor().getName().equals(entityToCompare.getColor().getName())) {
            map.put("Product color", this.getColor().getName() + "#" + entityToCompare.getColor().getName());
        }
        if (!this.getSize().getName().equals(entityToCompare.getSize().getName())) {
            map.put("Product size", this.getSize().getName() + "#" + entityToCompare.getSize().getName());
        }
        if (!this.getFabricType().getName().equals(entityToCompare.getFabricType().getName())) {
            map.put("Product fabric", this.getFabricType().getName() + "#" + entityToCompare.getFabricType().getName());
        }
        return map;
    }
}