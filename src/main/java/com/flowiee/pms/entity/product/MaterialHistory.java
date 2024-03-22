package com.flowiee.pms.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flowiee.pms.base.BaseEntity;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "material_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MaterialHistory extends BaseEntity implements Serializable {
    @Serial
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "newValue")
    private String newValue;

    @Override
    public String toString() {
        return "MaterialHistory [id=" + super.getId() + ", material=" + material + ", title=" + title + ", fieldName=" + fieldName + ", oldValue=" + oldValue + ", newValue=" + newValue + "]";
    }
}