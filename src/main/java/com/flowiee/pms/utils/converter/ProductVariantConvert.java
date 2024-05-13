package com.flowiee.pms.utils.converter;

import com.flowiee.pms.entity.category.Category;
import com.flowiee.pms.entity.product.Product;
import com.flowiee.pms.entity.product.ProductDetail;
import com.flowiee.pms.entity.sales.GarmentFactory;
import com.flowiee.pms.entity.sales.Supplier;
import com.flowiee.pms.model.dto.ProductVariantDTO;
import com.flowiee.pms.utils.AppConstants;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductVariantConvert {
    public static List<ProductVariantDTO> entitiesToDTOs(List<ProductDetail> inputEntities) {
        List<ProductVariantDTO> outputDTOs = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(inputEntities)) {
            for (ProductDetail p : inputEntities) {
                outputDTOs.add(entityToDTO(p));
            }
        }
        return outputDTOs;
    }

    public static List<ProductDetail> dTOsToEntities(List<ProductVariantDTO> inputDTOs) {
        List<ProductDetail> outputEntities = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(inputDTOs)) {
            for (ProductVariantDTO p : inputDTOs) {
                outputEntities.add(dtoToEntity(p));
            }
        }
        return outputEntities;
    }

    public static ProductVariantDTO entityToDTO(ProductDetail inputEntity) {
        if (ObjectUtils.isEmpty(inputEntity)) {
            return null;
        }
        ProductVariantDTO outputDTO = new ProductVariantDTO();
        outputDTO.setId(inputEntity.getId());
        outputDTO.setProductId(inputEntity.getProduct().getId());
        outputDTO.setVariantCode(inputEntity.getVariantCode());
        outputDTO.setVariantName(inputEntity.getVariantName());
        outputDTO.setStorageQty(inputEntity.getStorageQty());
        outputDTO.setSoldQty(inputEntity.getSoldQty());
        outputDTO.setStatus(inputEntity.getStatus());
        if (ObjectUtils.isNotEmpty(inputEntity.getProduct().getProductType())) {
            outputDTO.setProductTypeId(inputEntity.getProduct().getProductType().getId());
            outputDTO.setProductTypeName(inputEntity.getProduct().getProductType().getName());
        }
        if (ObjectUtils.isNotEmpty(inputEntity.getProduct().getBrand())) {
            outputDTO.setBrandId(inputEntity.getProduct().getBrand().getId());
            outputDTO.setBrandName(inputEntity.getProduct().getBrand().getName());
        }
        if (ObjectUtils.isNotEmpty(inputEntity.getProduct().getUnit())) {
            outputDTO.setUnitId(inputEntity.getProduct().getUnit().getId());
            outputDTO.setUnitName(inputEntity.getProduct().getUnit().getName());
        }
        outputDTO.setColorId(inputEntity.getColor().getId());
        outputDTO.setColorName(inputEntity.getColor().getName());
        outputDTO.setSizeId(inputEntity.getSize().getId());
        outputDTO.setSizeName(inputEntity.getSize().getName());
        outputDTO.setFabricTypeId(inputEntity.getFabricType().getId());
        outputDTO.setFabricTypeName(inputEntity.getFabricType().getName());
        if (ObjectUtils.isNotEmpty(inputEntity.getGarmentFactory())) {
            outputDTO.setGarmentFactoryId(inputEntity.getGarmentFactory().getId());
            outputDTO.setGarmentFactoryName(inputEntity.getGarmentFactory().getName());
        }
        if (ObjectUtils.isNotEmpty(inputEntity.getSupplier())) {
            outputDTO.setSupplierId(inputEntity.getSupplier().getId());
            outputDTO.setSupplierName(inputEntity.getSupplier().getName());
        }
        outputDTO.setOriginalPrice(inputEntity.getOriginalPrice());
        outputDTO.setDiscountPrice(inputEntity.getDiscountPrice());
        outputDTO.setPriceMaxDiscount(null);
        outputDTO.setPriceAfterDiscount(null);
        outputDTO.setUnitCurrency(null);
//        if (AppConstants.PRODUCT_STATUS.A.name().equals(inputEntity.getStatus())) {
//            outputDTO.setStatus(AppConstants.PRODUCT_STATUS.A.getLabel());
//        } else if (AppConstants.PRODUCT_STATUS.I.name().equals(inputEntity.getStatus())) {
//            outputDTO.setStatus(AppConstants.PRODUCT_STATUS.I.getLabel());
//        }
        outputDTO.setDefectiveQty(inputEntity.getDefectiveQty());
        outputDTO.setAvailableSalesQty(outputDTO.getStorageQty() - outputDTO.getDefectiveQty());
        outputDTO.setRetailPrice(inputEntity.getRetailPrice());
        outputDTO.setRetailPriceDiscount(inputEntity.getRetailPriceDiscount());
        outputDTO.setWholesalePrice(inputEntity.getWholesalePrice());
        outputDTO.setWholesalePriceDiscount(inputEntity.getWholesalePriceDiscount());
        outputDTO.setPurchasePrice(inputEntity.getPurchasePrice());
        outputDTO.setCostPrice(inputEntity.getCostPrice());
        outputDTO.setWeight(inputEntity.getWeight());
        outputDTO.setNote(inputEntity.getNote());
        outputDTO.setStatus(inputEntity.getStatus());
        return outputDTO;
    }

    public static ProductDetail dtoToEntity(ProductVariantDTO inputDTO) {
        if (inputDTO == null) {
            return null;
        }
        ProductDetail outputEntity = new ProductDetail();

        if (inputDTO.getProduct() != null)
            outputEntity.setProduct(inputDTO.getProduct());
        if (outputEntity.getProduct() == null && inputDTO.getProductId() != null)
            outputEntity.setProduct(new Product(inputDTO.getProductId()));

        outputEntity.setVariantCode(inputDTO.getVariantCode());
        outputEntity.setVariantName(inputDTO.getVariantName());

        if (inputDTO.getColor() != null)
            outputEntity.setColor(inputDTO.getColor());
        if (outputEntity.getColor() == null && inputDTO.getColorId() != null)
            outputEntity.setColor(new Category(inputDTO.getColorId(), inputDTO.getColorName()));

        if (inputDTO.getSize() != null)
            outputEntity.setSize(inputDTO.getSize());
        if (outputEntity.getSize() == null && inputDTO.getSizeId() != null)
            outputEntity.setSize(new Category(inputDTO.getSizeId(), inputDTO.getSizeName()));

        if (inputDTO.getFabricType() != null)
            outputEntity.setFabricType(inputDTO.getFabricType());
        if (outputEntity.getFabricType() == null && inputDTO.getFabricTypeId() != null)
            outputEntity.setFabricType(new Category(inputDTO.getFabricTypeId(), inputDTO.getFabricTypeName()));

        if (inputDTO.getGarmentFactory() != null)
            outputEntity.setGarmentFactory(inputDTO.getGarmentFactory());
        if (outputEntity.getGarmentFactory() == null && inputDTO.getGarmentFactoryId() != null)
            outputEntity.setGarmentFactory(new GarmentFactory(inputDTO.getGarmentFactoryId()));

        if (inputDTO.getSupplier() != null)
            outputEntity.setSupplier(inputDTO.getSupplier());
        if (outputEntity.getSupplier() == null && inputDTO.getSupplierId() != null)
            outputEntity.setSupplier(new Supplier(inputDTO.getSupplierId()));

        outputEntity.setId(inputDTO.getId());
        outputEntity.setStorageQty(inputDTO.getStorageQty());
        outputEntity.setSoldQty(inputDTO.getSoldQty());
        outputEntity.setDefectiveQty(inputDTO.getDefectiveQty());
        outputEntity.setOriginalPrice(inputDTO.getOriginalPrice());//Remove in future
        outputEntity.setDiscountPrice(inputDTO.getDiscountPrice());//Remove in future
        outputEntity.setPurchasePrice(inputDTO.getPurchasePrice());
        outputEntity.setCostPrice(inputDTO.getCostPrice());
        outputEntity.setRetailPrice(inputDTO.getRetailPrice());
        outputEntity.setRetailPriceDiscount(inputDTO.getRetailPriceDiscount());
        outputEntity.setWholesalePrice(inputDTO.getWholesalePrice());
        outputEntity.setWholesalePriceDiscount(inputDTO.getWholesalePriceDiscount());
        outputEntity.setWeight(inputDTO.getWeight());
        outputEntity.setNote(inputDTO.getNote());
        outputEntity.setStatus(inputDTO.getStatus());
        return outputEntity;
    }
}