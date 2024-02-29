package com.flowiee.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flowiee.app.entity.Material;
import com.flowiee.app.entity.Price;
import com.flowiee.app.entity.ProductVariant;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PriceDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private ProductVariant productVariant;
    private Material material;
    private String type;
    private BigDecimal original;
    private BigDecimal discount;
    private String status;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date createdAt;
    private Integer createdBy;

    public static PriceDTO fromPrice(Price p) {
        if (p != null) {
            PriceDTO dto = new PriceDTO();
            dto.setId(p.getId());
            dto.setProductVariant(p.getProductVariant());
            dto.setMaterial(p.getMaterial());
            dto.setType(p.getType());
            dto.setOriginal(p.getGiaBan());
            dto.setDiscount(p.getDiscount());
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