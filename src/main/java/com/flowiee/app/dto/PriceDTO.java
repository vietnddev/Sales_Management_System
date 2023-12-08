package com.flowiee.app.dto;

import com.flowiee.app.entity.Material;
import com.flowiee.app.entity.Price;
import com.flowiee.app.entity.ProductVariant;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PriceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private ProductVariant productVariant;
    private Material material;
    private Double giaMua;
    private String unitBuy;
    private Double giaBan;
    private String unitSell;
    private String status;
    private Date createdAt;
    private Integer createdBy;

    public static PriceDTO fromPrice(Price p) {
        PriceDTO dto = new PriceDTO();
        dto.setId(p.getId());
        dto.setProductVariant(p.getProductVariant());
        dto.setMaterial(p.getMaterial());
        dto.setGiaMua(p.getGiaMua());
        dto.setUnitBuy(p.getUnitBuy());
        dto.setGiaBan(p.getGiaBan());
        dto.setUnitSell(p.getUnitSell());
        dto.setStatus(p.getStatus());
        dto.setCreatedAt(p.getCreatedAt());
        dto.setCreatedBy(p.getCreatedBy());
        return dto;
    }

    public static List<PriceDTO> fromPrices(List<Price> lp) {
        List<PriceDTO> dto = new ArrayList<>();
        for (Price p : lp) {
            dto.add(fromPrice(p));
        }
        return dto;
    }
}