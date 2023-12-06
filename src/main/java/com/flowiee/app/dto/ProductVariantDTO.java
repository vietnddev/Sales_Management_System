package com.flowiee.app.dto;

import com.flowiee.app.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ProductVariantDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer productVariantId;
    private Integer productId;
    private String code;
    private String name;
    //private int quantity;
    private Integer storageQty;
    //private int sell;
    private Integer soldQty;
    private String description;
    private String status;
    //private Category color; //future remove
    private Integer colorId;
    private String colorName;
    //private Category size; //future remove
    private Integer sizeId;
    private String sizeName;
    //private Category fabricType; //future remove
    private Integer fabricTypeId;
    private String fabricTypeName;
    //private GarmentFactory garmentFactory; //future remove
    private Integer garmentFactoryId;
    private String garmentFactoryName;
    //private Supplier supplier; //future remove
    private Integer supplierId;
    private String supplierName;
    //private TicketImportGoods ticketImportGoods; //future remove
    private Integer ticketImportGoodsId;
    private String ticketImportGoodsTitle;
    private Integer discountPercent;
    private Integer priceBuyId;
    private Float priceBuyValue;
    private Integer priceSellId;
    private Float priceSellValue;
    private Float priceMaxDiscount;
    private Float priceAfterDiscount;
    private String unitCurrency;
    private List<Price> listPrices;

    public static ProductVariantDTO fromProductVariant(ProductVariant input) {
        ProductVariantDTO dto = new ProductVariantDTO();
        dto.setProductVariantId(input.getId());
        dto.setProductId(input.getProduct().getId());
        dto.setCode(input.getMaSanPham());
        dto.setName(input.getTenBienThe());
        //dto.setQuantity(input.getSoLuongKho());
        dto.setStorageQty(input.getSoLuongKho());
        //dto.setSell(input.getSoLuongDaBan());
        dto.setSoldQty(input.getSoLuongKho());
        dto.setDescription(input.getMaSanPham());
        dto.setStatus(input.getTrangThai());
        //dto.setColor(input.getColor());
        dto.setColorId(input.getColor().getId());
        dto.setColorName(input.getColor().getName());
        //dto.setSize(input.getSize());
        dto.setSizeId(input.getSize().getId());
        dto.setSizeName(input.getSize().getName());
        //dto.setFabricType(input.getFabricType());
        dto.setFabricTypeId(input.getFabricType().getId());
        dto.setFabricTypeName(input.getFabricType().getName());
        //dto.setGarmentFactory(input.getGarmentFactory());
        dto.setGarmentFactoryId(input.getGarmentFactory().getId());
        dto.setGarmentFactoryName(input.getGarmentFactory().getName());
        //dto.setSupplier(input.getSupplier());
        dto.setSupplierId(input.getSupplier().getId());
        dto.setSupplierName(input.getSupplier().getName());
        //dto.setTicketImportGoods(input.getTicketImportGoods());
        dto.setTicketImportGoodsId(input.getTicketImportGoods().getId());
        dto.setTicketImportGoodsTitle(input.getTicketImportGoods().getTitle());
        dto.setDiscountPercent(null);
        dto.setPriceBuyId(null);
        dto.setPriceBuyValue(null);
        dto.setPriceSellId(null);
        dto.setPriceSellValue(null);
        dto.setPriceMaxDiscount(null);
        dto.setPriceAfterDiscount(null);
        dto.setUnitCurrency(null);
        dto.setListPrices(null);
        return dto;
    }
}