package com.flowiee.app.storage.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.danhmuc.entity.DonViTinh;
import com.flowiee.app.sanpham.entity.GoodsImport;
import com.flowiee.app.sanpham.entity.MaterialHistory;
import com.flowiee.app.sanpham.entity.Price;
import com.flowiee.app.sanpham.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "material_temp")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MaterialTemp extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_import_id")
    private GoodsImport goodsImport;

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
    private DonViTinh unit;

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

    public static MaterialTemp convertFromMaterial(Material material) {
        MaterialTemp materialTemp = new MaterialTemp();
        materialTemp.setId(material.getId());
        materialTemp.setGoodsImport(material.getGoodsImport());
        materialTemp.setSupplier(material.getSupplier());
        materialTemp.setCode(material.getCode());
        materialTemp.setName(material.getName());
        materialTemp.setQuantity(material.getQuantity());
        materialTemp.setUnit(material.getUnit());
        materialTemp.setLocation(material.getLocation());
        materialTemp.setNote(material.getNote());
        materialTemp.setStatus(material.isStatus());
        materialTemp.setListMaterialHistory(material.getListMaterialHistory());
        materialTemp.setListPrice(material.getListPrice());
        return materialTemp;
    }
}