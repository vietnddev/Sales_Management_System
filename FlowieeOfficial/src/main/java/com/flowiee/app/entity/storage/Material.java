package com.flowiee.app.entity.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.category.entity.DonViTinh;
import com.flowiee.app.entity.product.GoodsImport;
import com.flowiee.app.entity.product.MaterialHistory;
import com.flowiee.app.entity.product.Price;
import com.flowiee.app.entity.product.Supplier;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "material")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Material extends BaseEntity implements Serializable {
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
}