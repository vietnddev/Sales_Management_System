package com.flowiee.app.dto;

import com.flowiee.app.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ProductVariantDTO extends ProductVariant implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer productVariantId;
    private Integer productId;
    private String code;
    private String name;
    private int quantity;
    private Integer storageQty; //new
    private int sell;
    private Integer soldQty; //new
    private String description;
    private String status;
    private Category color; //future remove
    private Integer colorId;
    private String colorName;
    private Category size; //future remove
    private Integer sizeId;
    private String sizeName;
    private Category fabricType; //future remove
    private Integer fabricTypeId;
    private String fabricTypeName;
    private GarmentFactory garmentFactory; //future remove
    private Integer garmentFactoryId;
    private String garmentFactoryName;
    private Supplier supplier; //future remove
    private Integer supplierId;
    private Integer supplierName;
    private TicketImportGoods ticketImportGoods; //future remove
    private Integer ticketImportGoodsId;
    private Integer ticketImportGoodsTitle;
    private Integer discountPercent;
    private Integer priceBuyId;
    private Float priceBuyValue;
    private Integer priceSellId;
    private Float priceSellValue;
    private Float priceMaxDiscount;
    private Float priceAfterDiscount;
    private String unitCurrency;
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