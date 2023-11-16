package com.flowiee.app.model.product;

import com.flowiee.app.category.Category;
import com.flowiee.app.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ProductVariantResponse extends ProductVariant implements Serializable {
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
    private GoodsImport goodsImport;
    private List<Price> prices;

    public static ProductVariantResponse fromProductVariant(ProductVariant input) {
        ProductVariantResponse response = new ProductVariantResponse();
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
        response.setGoodsImport(input.getGoodsImport());
        return response;
    }
}