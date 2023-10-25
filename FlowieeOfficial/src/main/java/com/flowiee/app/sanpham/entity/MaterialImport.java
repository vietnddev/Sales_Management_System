package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.danhmuc.entity.DonViTinh;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "material_import")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MaterialImport extends BaseEntity implements Serializable {
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

    @Column(name = "unit_price")
    private Float unitPrice;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "note")
    private String note;
}