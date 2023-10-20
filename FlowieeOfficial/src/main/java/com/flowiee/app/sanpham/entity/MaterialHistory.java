package com.flowiee.app.sanpham.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.app.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
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