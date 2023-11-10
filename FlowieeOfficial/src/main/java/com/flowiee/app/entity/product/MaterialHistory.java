package com.flowiee.app.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.base.BaseEntity;
import com.flowiee.app.storage.entity.Material;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "material_history")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MaterialHistory extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(name = "action")
    private String action;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "newValue")
    private String newValue;

    @Column(name = "note")
    private String note;
}