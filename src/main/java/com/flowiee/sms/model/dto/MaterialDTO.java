package com.flowiee.sms.model.dto;

import com.flowiee.sms.entity.Material;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MaterialDTO extends Material implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer supplierId;
    private String supplierName;
    private Integer unitId;
    private String unitName;

    public static MaterialDTO fromMaterial(Material material) {
        MaterialDTO dto = new MaterialDTO();
        dto.setId(material.getId());
        dto.setCode(material.getCode());
        dto.setName(dto.getName());
        if (material.getSupplier() != null) {
            dto.setSupplierId(material.getSupplier().getId());
            dto.setSupplierName(material.getSupplier().getName());
        }
        if (material.getUnit() != null) {
            dto.setUnitId(material.getUnit().getId());
            dto.setUnitName(material.getUnit().getName());
        }
        dto.setQuantity(material.getQuantity());
        dto.setLocation(material.getLocation());
        dto.setNote(material.getNote());
        dto.setStatus(material.isStatus());
        return dto;
    }

    public static List<MaterialDTO> fromMaterials(List<Material> materials) {
        List<MaterialDTO> list = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(materials)) {
            for (Material m : materials) {
                list.add(MaterialDTO.fromMaterial(m));
            }
        }
        return list;
    }
}