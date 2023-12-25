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
    private String giaMua;
    private String unitBuy;
    private String giaBan;
    private String unitSell;
    private String status;
    private Date createdAt;
    private Integer createdBy;

    public static PriceDTO fromPrice(Price p) {
        if (p != null) {
            PriceDTO dto = new PriceDTO();
            dto.setId(p.getId());
            dto.setProductVariant(p.getProductVariant());
            dto.setMaterial(p.getMaterial());
            dto.setGiaMua(String.valueOf(p.getGiaMua()));
            dto.setUnitBuy(p.getUnitBuy());
            dto.setGiaBan(String.valueOf(p.getGiaBan()));
            dto.setUnitSell(p.getUnitSell());
            dto.setStatus(p.getStatus());
            dto.setCreatedAt(p.getCreatedAt());
            dto.setCreatedBy(p.getCreatedBy());
            return dto;
        }
        return null;
    }

    public static List<PriceDTO> fromPrices(List<Price> lp) {
        List<PriceDTO> dto = new ArrayList<>();
        for (Price p : lp) {
            dto.add(fromPrice(p));
        }
        return dto;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PriceDTO [id=");
		builder.append(id);
		builder.append(", productVariant=");
		builder.append(productVariant);
		builder.append(", material=");
		builder.append(material);
		builder.append(", giaMua=");
		builder.append(giaMua);
		builder.append(", unitBuy=");
		builder.append(unitBuy);
		builder.append(", giaBan=");
		builder.append(giaBan);
		builder.append(", unitSell=");
		builder.append(unitSell);
		builder.append(", status=");
		builder.append(status);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append("]");
		return builder.toString();
	}
}