package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.product.Material;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class MaterialDTO extends Material implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer supplierId;
    private String supplierName;
    private Integer unitId;
    private String unitName;
}