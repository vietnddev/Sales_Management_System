package com.flowiee.app.dto;

import com.flowiee.app.entity.Category;
import com.flowiee.app.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ProductVariantDTO extends ProductVariant implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String code;
    private String name;
    private int quantity;
    private int sell;
    private String description;
    private String status;
    private Category color;
    private Category size;
    private Category fabricType;
    private GarmentFactory garmentFactory;
    private Supplier supplier;
    private TicketImportGoods ticketImportGoods;
    private List<Price> prices;

    public static ProductVariantDTO fromProductVariant(ProductVariant input) {
        ProductVariantDTO response = new ProductVariantDTO();
        response.setProduct(input.getProduct());
        response.setId(input.getId());
        response.setCode(input.getMaSanPham());
        response.setName(input.getTenBienThe());
        response.setQuantity(input.getSoLuongKho());
        response.setSell(input.getSoLuongDaBan());
        response.setDescription(input.getMaSanPham());
        response.setStatus(input.getTrangThai());
        response.setColor(input.getColor());
        response.setSize(input.getSize());
        response.setFabricType(input.getFabricType());
        response.setGarmentFactory(input.getGarmentFactory());
        response.setSupplier(input.getSupplier());
        response.setTicketImportGoods(input.getTicketImportGoods());
        return response;
    }
}