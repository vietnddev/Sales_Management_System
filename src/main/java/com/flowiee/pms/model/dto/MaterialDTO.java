package com.flowiee.pms.model.dto;

import com.flowiee.pms.entity.product.Material;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MaterialDTO extends Material implements Serializable {
    @Serial
    static final long serialVersionUID = 1L;

    Integer supplierId;
    String supplierName;
    Integer unitId;
    String unitName;
}