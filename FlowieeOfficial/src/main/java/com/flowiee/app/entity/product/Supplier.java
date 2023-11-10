package com.flowiee.app.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.storage.entity.Material;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "supplier")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Supplier extends BaseEntity implements Serializable {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "product_provided")
    private String productProvided;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<GoodsImport> listGoodsImport;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<ProductVariant> listProductVariant;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    private List<Material> listMaterial;

    public Supplier(Integer id, String name) {
        super.id = id;
        this.name = name;
    }
}